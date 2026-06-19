package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Input representation to register a new device.
 *
 * @since 1.0
 */
public record CreateDeviceResource(
        @NotNull Integer ownerId,
        @NotBlank String serialNumber,
        @NotBlank String deviceType) {
}
