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
                AnomalyType.valueOf(resource.detectedParameters().toUpperCase()),
                resource.severityScore());
    }
}