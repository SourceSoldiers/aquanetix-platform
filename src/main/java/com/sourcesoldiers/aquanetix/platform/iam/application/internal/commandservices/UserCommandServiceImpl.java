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

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    /*
    @Override
    @Transactional
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", command.username()));
        }
        if (!hashingService.matches(command.password(), user.get().getPasswordHash().value())) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid username or password"));
        }
        var token = tokenService.generateToken(user.get().getUsername());
        return Result.success(ImmutablePair.of(user.get(), token));
    }
    */

    @Override
    @Transactional
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            return Result.failure(ApplicationError.conflict("User", "Username already exists"));
        }
        var requestedRoles = command.roles() == null || command.roles().isEmpty()
                ? java.util.List.of(Role.getDefaultRole())
                : command.roles();

        var roles = requestedRoles.stream()
                .map(role -> roleRepository.findByName(role.getName()))
                .toList();

        if (roles.stream().anyMatch(java.util.Optional::isEmpty)) {
            return Result.failure(ApplicationError.notFound("Role", "one or more role names"));
        }

        var resolvedRoles = roles.stream()
                .map(java.util.Optional::get)
                .toList();

        var user = User.create(command.username(), PasswordHash.of(hashingService.encode(command.password())), resolvedRoles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username())
                .<Result<User, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.unexpected("sign-up", "Created user could not be reloaded")));
    }

}