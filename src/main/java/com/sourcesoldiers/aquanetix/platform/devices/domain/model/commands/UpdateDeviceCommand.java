package com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceStatus;

import java.time.OffsetDateTime;

public record UpdateDeviceCommand(
        Long id,
        DeviceStatus currentStatus,
        OffsetDateTime lastTelemetrySync,
        String name,
        String location,
        String unit,
        Double currentValue) {
}
