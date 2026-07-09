package com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.commands;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyType;

/**
 * Command to register a new quality analysis.
 *
 * @param sensorSourceId    identifier of the sensor that generated the data
 * @param detectedParameters type of anomaly detected
 * @param severityScore     numerical severity score (0-10)
 * @since 1.0
 */
public record CreateQualityAnalysisCommand(
        Integer sensorSourceId,
        AnomalyType detectedParameters,
        Double severityScore) {
}
