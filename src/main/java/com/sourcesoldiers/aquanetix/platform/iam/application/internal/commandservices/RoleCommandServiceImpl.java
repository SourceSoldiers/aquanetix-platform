package com.sourcesoldiers.aquanetix.platform.iam.application.internal.commandservices;


import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SeedRolesCommand;
import com.sourcesoldiers.aquanetix.platform.iam.application.commandservices.RoleCommandService;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.entities.Role;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.valueobjects.Roles;
import com.sourcesoldiers.aquanetix.platform.iam.domain.repositories.RoleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Implementation of {@link RoleCommandService} to handle {@link SeedRolesCommand}.
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}