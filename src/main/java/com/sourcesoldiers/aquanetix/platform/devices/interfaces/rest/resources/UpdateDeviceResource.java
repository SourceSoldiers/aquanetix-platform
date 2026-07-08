package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

/**
 * Input representation to update a device's current status and last
 * telemetry sync timestamp.
 *
 * @since 1.0
 */
public record UpdateDeviceResource(
        @NotBlank String currentStatus,
        @NotNull OffsetDateTime lastTelemetrySync,
        String name,
        String location,
        String unit,
        Double currentValue) {
}
