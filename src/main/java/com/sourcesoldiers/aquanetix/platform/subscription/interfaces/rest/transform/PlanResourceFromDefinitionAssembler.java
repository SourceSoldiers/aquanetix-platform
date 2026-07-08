package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.valueobjects.PlanDefinition;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.PlanResource;

public final class PlanResourceFromDefinitionAssembler {

    private PlanResourceFromDefinitionAssembler() {
    }

    public static PlanResource toResource(PlanDefinition definition) {
        return new PlanResource(
                definition.name(),
                definition.monthlyCost(),
                definition.maxDevices(),
                definition.isUnlimited()
        );
    }
}
