package com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries;

/**
 * Query to retrieve a single device by its identifier.
 *
 * @param deviceId identifier of the device
 * @since 1.0
 */
public record GetDeviceByIdQuery(Long deviceId) {
}
