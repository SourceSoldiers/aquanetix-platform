package com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.entities.Role;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.valueobjects.PasswordHash;
import com.sourcesoldiers.aquanetix.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root.
 */
@Getter
public class User extends AbstractDomainAggregateRoot<User> {

    @Setter
    private Long id;

    private String username;

    private PasswordHash passwordHash;

    private final Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User(String username, PasswordHash passwordHash) {
        this();
        setUsername(username);
        changePasswordHash(passwordHash);
    }

    public User(String username, PasswordHash passwordHash, List<Role> roles) {
        this(username, passwordHash);
        addRoles(roles);
    }

    public static User create(String username, PasswordHash passwordHash, List<Role> roles) {
        return new User(username, passwordHash, roles);
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        this.username = username.trim().toLowerCase();
    }

    public String getEmail() {
        return username;
    }

    public void changePasswordHash(PasswordHash passwordHash) {
        if (passwordHash == null) {
            throw new IllegalArgumentException("Password hash is required");
        }
        this.passwordHash = passwordHash;
    }

    public User addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.roles.add(role);
        return this;
    }

    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        validatedRoleSet.forEach(this::addRole);
        return this;
    }

    public void replaceRoles(Set<Role> roles) {
        this.roles.clear();
        if (roles != null) {
            roles.forEach(this::addRole);
        }
        if (this.roles.isEmpty()) {
            this.roles.add(Role.getDefaultRole());
        }
    }
}
