package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyStatus;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyType;
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
                formatAnomalyType(entity.getDetectedParameters()),
                formatAnomalyStatus(entity.getAnomalyStatus()),
                entity.getSeverityScore(),
                entity.getHasContaminationPeakPrediction(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private static String formatAnomalyType(AnomalyType type) {
        return switch (type) {
            case PH -> "PH";
            case TURBIDITY -> "Turbidity";
            case PRESSURE -> "Pressure";
            case LEVEL -> "Level";
            case CHLORINE -> "Chlorine";
            case FLOW -> "Flow";
            case DISSOLVED_OXYGEN -> "DissolvedOxygen";
            case TEMPERATURE -> "Temperature";
        };
    }

    private static String formatAnomalyStatus(AnomalyStatus status) {
        return switch (status) {
            case DETECTED -> "Detected";
            case EVALUATED -> "Evaluated";
            case CONFIRMED -> "Confirmed";
            case DISMISSED -> "Dismissed";
        };
    }
}
