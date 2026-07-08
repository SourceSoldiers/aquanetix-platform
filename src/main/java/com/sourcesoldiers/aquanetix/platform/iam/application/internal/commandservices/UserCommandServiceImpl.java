package com.sourcesoldiers.aquanetix.platform.iam.application.internal.commandservices;


import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SignInCommand;
import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SignUpCommand;
import com.sourcesoldiers.aquanetix.platform.iam.application.commandservices.UserCommandService;
import com.sourcesoldiers.aquanetix.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.sourcesoldiers.aquanetix.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates.User;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.entities.Role;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.valueobjects.PasswordHash;
import com.sourcesoldiers.aquanetix.platform.iam.domain.repositories.RoleRepository;
import com.sourcesoldiers.aquanetix.platform.iam.domain.repositories.UserRepository;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.ApplicationError;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.Result;
import com.sourcesoldiers.aquanetix.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CreateSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.valueobjects.PlanCatalog;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User command service implementation.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;
    private final SubscriptionCommandService subscriptionCommandService;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService,
            RoleRepository roleRepository,
            SubscriptionCommandService subscriptionCommandService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @Override
    @Transactional
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command) {
        if (command.email() == null || command.email().isBlank() ||
                command.password() == null || command.password().isBlank()) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid email or password"));
        }

        var email = command.email().trim().toLowerCase();
        var user = userRepository.findByUsername(email);
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid email or password"));
        }
        if (!hashingService.matches(command.password(), user.get().getPasswordHash().value())) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid email or password"));
        }
        var token = tokenService.generateToken(user.get().getEmail());
        return Result.success(ImmutablePair.of(user.get(), token));
    }

    @Override
    @Transactional
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignUpCommand command) {
        if (command.email() == null || command.email().isBlank() ||
                command.password() == null || command.password().isBlank()) {
            return Result.failure(ApplicationError.validationError("credentials", "Email and password are required"));
        }

        if (!PlanCatalog.isValidPlan(command.plan())) {
            return Result.failure(ApplicationError.validationError("plan", "Invalid plan. Choose Basic, Smart City or Industrial"));
        }

        var email = command.email().trim().toLowerCase();

        if (userRepository.existsByUsername(email)) {
            return Result.failure(ApplicationError.conflict("User", "A user with that email already exists"));
        }
        var requestedRoles = java.util.List.of(Role.getDefaultRole());

        var roles = requestedRoles.stream()
                .map(role -> roleRepository.findByName(role.getName()))
                .toList();

        if (roles.stream().anyMatch(java.util.Optional::isEmpty)) {
            return Result.failure(ApplicationError.notFound("Role", "one or more role names"));
        }

        var resolvedRoles = roles.stream()
                .map(java.util.Optional::get)
                .toList();

        var user = User.create(email, PasswordHash.of(hashingService.encode(command.password())), resolvedRoles);
        var saved = userRepository.save(user);
        var subscription = subscriptionCommandService.handle(
                new CreateSubscriptionCommand(saved.getId().intValue(), command.plan(), "Active"));

        if (subscription.isEmpty()) {
            return Result.failure(ApplicationError.validationError("plan", "Invalid plan. Choose Basic, Smart City or Industrial"));
        }

        var token = tokenService.generateToken(saved.getEmail());

        return Result.success(ImmutablePair.of(saved, token));
    }

}
