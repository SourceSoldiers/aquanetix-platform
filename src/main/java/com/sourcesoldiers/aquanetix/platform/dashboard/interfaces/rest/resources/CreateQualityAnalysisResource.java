package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Input representation to register a new quality analysis.
 *
 * @since 1.0
 */
public record CreateQualityAnalysisResource(
        @NotNull Integer sensorSourceId,
        @NotBlank String detectedParameters,
        @Schema(
                description = "Severity score normalized to the 0-10 range.",
                minimum = "0",
                maximum = "10",
                example = "8.5")
        @NotNull Double severityScore) {
}
