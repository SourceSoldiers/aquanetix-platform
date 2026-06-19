package com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries;

/**
 * Query to retrieve all threshold configurations belonging to a device.
 *
 * @param deviceId identifier of the device
 * @since 1.0
 */
public record GetThresholdsByDeviceIdQuery(Long deviceId) {
}
