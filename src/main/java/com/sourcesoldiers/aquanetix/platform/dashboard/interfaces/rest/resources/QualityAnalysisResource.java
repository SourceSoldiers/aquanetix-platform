package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.resources;

import java.time.OffsetDateTime;

/**
 * Output representation of a {@code QualityAnalysis} exposed through the REST API.
 *
 * @since 1.0
 */
public record QualityAnalysisResource(
        Long id,
        Integer sensorSourceId,
        String detectedParameters,
        String anomalyStatus,
        Double severityScore,
        Boolean hasContaminationPeakPrediction,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
