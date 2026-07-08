package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.Destination;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.DestinationResource;

public final class DestinationResourceFromEntityAssembler {

    private DestinationResourceFromEntityAssembler() {
    }

    public static DestinationResource toResourceFromEntity(Destination entity) {
        return new DestinationResource(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getDescription()
        );
    }
}
