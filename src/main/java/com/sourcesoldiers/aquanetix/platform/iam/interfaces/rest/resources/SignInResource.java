package com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource received to authenticate an existing user.
 */
@Schema(
        name = "SignInResource",
        description = "User sign-in request with credentials",
        example = "{\"email\": \"john.doe@example.com\", \"password\": \"SecurePass123!\"}"
)
public record SignInResource(
        @Schema(
                description = "User email",
                example = "john.doe@example.com",
                minLength = 3,
                maxLength = 50
        )
        String email,

        @Schema(
                description = "User password",
                example = "SecurePass123!",
                minLength = 8,
                maxLength = 255
        )
        String password
) {
}
