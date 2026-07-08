package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.commands.CreateQualityAnalysisCommand;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyType;
import com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.resources.CreateQualityAnalysisResource;

/**
 * Assembles a {@link CreateQualityAnalysisCommand} from a {@link CreateQualityAnalysisResource}.
 *
 * @since 1.0
 */
public final class CreateQualityAnalysisCommandFromResourceAssembler {

    private CreateQualityAnalysisCommandFromResourceAssembler() {
    }

    public static CreateQualityAnalysisCommand toCommandFromResource(CreateQualityAnalysisResource resource) {
        return new CreateQualityAnalysisCommand(
                resource.sensorSourceId(),
                parseAnomalyType(resource.detectedParameters()),
                resource.severityScore());
    }

    private static AnomalyType parseAnomalyType(String value) {
        return switch (value == null ? "" : value.trim().toLowerCase()) {
            case "ph" -> AnomalyType.PH;
            case "turbidity" -> AnomalyType.TURBIDITY;
            case "pressure" -> AnomalyType.PRESSURE;
            case "level" -> AnomalyType.LEVEL;
            case "chlorine" -> AnomalyType.CHLORINE;
            case "flow" -> AnomalyType.FLOW;
            case "dissolvedoxygen", "dissolved_oxygen" -> AnomalyType.DISSOLVED_OXYGEN;
            case "temperature" -> AnomalyType.TEMPERATURE;
            default -> throw new IllegalArgumentException("Invalid anomaly type: " + value);
        };
    }
}
