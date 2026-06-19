package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Input representation to create a threshold configuration for a device.
 *
 * @since 1.0
 */
public record CreateThresholdResource(
        @NotNull Double minValue,
        @NotNull Double maxValue,
        @NotBlank String unit,
        @NotBlank String alertLevel) {
}
