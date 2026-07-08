package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources;

import java.time.OffsetDateTime;

/**
 * Output representation of a {@code Device} exposed through the REST API.
 *
 * @since 1.0
 */
public record DeviceResource(
        Long id,
        Integer ownerId,
        String serialNumber,
        String deviceType,
        String currentStatus,
        OffsetDateTime lastTelemetrySync,
        String name,
        String location,
        String unit,
        Double currentValue,
        Long destinationId) {
}
