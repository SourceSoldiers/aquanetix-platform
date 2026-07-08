package com.sourcesoldiers.aquanetix.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource received to register a new IAM user.
 */
@Schema(
        name = "SignUpResource",
        description = "Public user sign-up request with credentials and chosen subscription plan",
        example = "{\"email\": \"john.doe@example.com\", \"password\": \"SecurePass123!\", \"plan\": \"Basic\"}"
)
public record SignUpResource(
        @Schema(
                description = "User email",
                example = "john.doe@example.com",
                minLength = 3,
                maxLength = 50
        )
        String email,

        @Schema(
                description = "User password (minimum 8 characters)",
                example = "SecurePass123!",
                minLength = 8,
                maxLength = 255
        )
        String password,

        @Schema(description = "Chosen subscription plan", example = "Basic")
        String plan) {
}
