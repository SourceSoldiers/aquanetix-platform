package com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SignUpCommand;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.entities.Role;
import com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.resources.SignUpResource;

import java.util.List;

/**
 * Assembler that translates {@link SignUpResource} into {@link SignUpCommand}.
 * Public registration never trusts roles sent by the client.
 */
public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.username(), resource.password(), List.of(Role.getDefaultRole()));
    }
}
