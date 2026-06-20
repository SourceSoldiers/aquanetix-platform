package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources;

public record CreateAlertResource(
        Long deviceId,
        String deviceName,
        String location,
        String type,
        String severity,
        String message,
        Double value,
        Double threshold
) {
}