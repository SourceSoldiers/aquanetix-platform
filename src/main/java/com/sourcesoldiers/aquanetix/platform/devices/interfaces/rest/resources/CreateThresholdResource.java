package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(
                description = "Alert level. Empty values are treated as Normal.",
                allowableValues = {"Normal", "Warning", "Critical"},
                example = "Normal",
                nullable = true)
        String alertLevel) {
}
