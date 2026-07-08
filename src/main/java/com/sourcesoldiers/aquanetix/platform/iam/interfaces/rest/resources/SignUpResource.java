package com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource received to register a new IAM user.
 */
@Schema(
        name = "SignUpRequest",
        description = "Public user sign-up request with credentials. The system assigns ROLE_USER by default",
        example = "{\"username\": \"john.doe\", \"password\": \"SecurePass123!\"}"
)
public record SignUpResource(
        @Schema(
                description = "Desired username",
                example = "john.doe",
                minLength = 3,
                maxLength = 50
        )
        String username,

        @Schema(
                description = "User password (minimum 8 characters)",
                example = "SecurePass123!",
                minLength = 8,
                maxLength = 255
        )
        String password) {
}