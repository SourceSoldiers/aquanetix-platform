package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.resources;

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
        @NotNull Double severityScore) {
}