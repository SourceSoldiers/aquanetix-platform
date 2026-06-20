package com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceType;

/**
 * Command to register a new device for an owner.
 *
 * @param ownerId      identifier of the owning user/account
 * @param serialNumber unique manufacturer serial number
 * @param deviceType   type of sensor the device represents
 * @since 1.0
 */
public record CreateDeviceCommand(Integer ownerId, String serialNumber, DeviceType deviceType) {
}
