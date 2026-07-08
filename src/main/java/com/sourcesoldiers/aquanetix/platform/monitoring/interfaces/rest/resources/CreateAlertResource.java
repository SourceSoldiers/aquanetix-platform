package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record CreateAlertResource(
        @NotNull @Positive Long deviceId,
        @NotBlank String deviceName,
        @NotBlank String location,
        @NotBlank String type,
        @NotBlank String severity,
        @NotBlank String message,
        OffsetDateTime timestamp,
        String status,
        @NotNull Double value,
        @NotNull Double threshold
) {
}
