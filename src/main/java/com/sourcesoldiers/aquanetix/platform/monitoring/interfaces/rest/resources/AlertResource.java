package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources;

public record AlertResource(
        Long id,
        Long deviceId,
        String deviceName,
        String location,
        String type,
        String severity,
        String message,
        String timestamp,
        String status,
        Double value,
        Double threshold
) {}