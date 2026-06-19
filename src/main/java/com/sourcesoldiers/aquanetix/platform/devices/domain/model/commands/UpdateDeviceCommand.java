package com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceStatus;

import java.time.OffsetDateTime;

/**
 * Command to update the current status and last telemetry sync timestamp of
 * an existing device.
 *
 * @param id                identifier of the device to update
 * @param currentStatus     new operational status
 * @param lastTelemetrySync timestamp of the last telemetry reading received
 * @since 1.0
 */
public record UpdateDeviceCommand(Long id, DeviceStatus currentStatus, OffsetDateTime lastTelemetrySync) {
}
