package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.resources.QualityAnalysisResource;

/**
 * Assembles a {@link QualityAnalysisResource} from a {@link QualityAnalysis} entity.
 *
 * @since 1.0
 */
public final class QualityAnalysisResourceFromEntityAssembler {

    private QualityAnalysisResourceFromEntityAssembler() {
    }

    public static QualityAnalysisResource toResourceFromEntity(QualityAnalysis entity) {
        return new QualityAnalysisResource(
                entity.getId(),
                entity.getSensorSourceId(),
                entity.getDetectedParameters().name(),
                entity.getAnomalyStatus().name(),
                entity.getSeverityScore(),
                entity.getHasContaminationPeakPrediction(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
