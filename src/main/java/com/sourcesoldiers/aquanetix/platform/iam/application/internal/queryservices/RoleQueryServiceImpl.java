package com.sourcesoldiers.aquanetix.platform.iam.application.internal.queryservices;
import com.sourcesoldiers.aquanetix.platform.iam.application.queries.GetAllRolesQuery;
import com.sourcesoldiers.aquanetix.platform.iam.application.queries.GetRoleByNameQuery;
import com.sourcesoldiers.aquanetix.platform.iam.application.queryservices.RoleQueryService;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.entities.Role;
import com.sourcesoldiers.aquanetix.platform.iam.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves IAM role read queries.
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {
    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> handle(GetRoleByNameQuery query) {
        return roleRepository.findByName(query.name());
    }
}