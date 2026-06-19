package com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects;

/**
 * Current operational status of a device, derived from the latest telemetry
 * received and the configured thresholds.
 *
 * @since 1.0
 */
public enum DeviceStatus {
    NORMAL,
    WARNING,
    ALERT,
    OFFLINE
}
