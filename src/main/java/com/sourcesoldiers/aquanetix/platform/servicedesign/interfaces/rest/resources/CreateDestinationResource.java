package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateDestinationResource(
        @NotBlank String name,
        String address,
        String description
) {
}
