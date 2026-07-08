package com.sourcesoldiers.aquanetix.platform.iam.application.internal.queryservices;

import com.sourcesoldiers.aquanetix.platform.iam.application.queries.GetAllUsersQuery;
import com.sourcesoldiers.aquanetix.platform.iam.application.queries.GetUserByIdQuery;
import com.sourcesoldiers.aquanetix.platform.iam.application.queries.GetUserByUsernameQuery;
import com.sourcesoldiers.aquanetix.platform.iam.application.queryservices.UserQueryService;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates.User;
import com.sourcesoldiers.aquanetix.platform.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves IAM user read queries.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}