package com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SignUpCommand;
import com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.resources.SignUpResource;

/**
 * Assembler that translates {@link SignUpResource} into {@link SignUpCommand}.
 * Public registration receives email, password and selected plan.
 */
public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.email(), resource.password(), resource.plan());
    }
}
