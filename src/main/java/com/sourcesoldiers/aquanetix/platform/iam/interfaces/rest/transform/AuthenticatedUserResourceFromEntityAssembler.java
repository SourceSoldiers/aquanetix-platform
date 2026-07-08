package com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates.User;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.entities.Role;
import com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembler that translates IAM authentication results into {@link AuthenticatedUserResource}.
 */
public class AuthenticatedUserResourceFromEntityAssembler {
    /**
     * Creates a resource from the authenticated {@link User} aggregate and issued bearer token.
     *
     * @param user authenticated user aggregate
     * @param token generated bearer token
     * @return resource used by the authentication endpoint response
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        var role = user.getRoles().stream()
                .findFirst()
                .map(Role::getStringName)
                .map(value -> value.replace("ROLE_", ""))
                .map(value -> value.charAt(0) + value.substring(1).toLowerCase())
                .orElse("User");

        return new AuthenticatedUserResource(user.getId(), user.getEmail(), role, token);
    }
}
