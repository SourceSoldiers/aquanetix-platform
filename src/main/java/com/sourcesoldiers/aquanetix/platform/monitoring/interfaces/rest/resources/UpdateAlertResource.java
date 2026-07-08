package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources;

import java.time.OffsetDateTime;

public record UpdateAlertResource(
        Long deviceId,
        String deviceName,
        String location,
        String type,
        String severity,
        String message,
        OffsetDateTime timestamp,
        String status,
        Double value,
        Double threshold
) {
}
