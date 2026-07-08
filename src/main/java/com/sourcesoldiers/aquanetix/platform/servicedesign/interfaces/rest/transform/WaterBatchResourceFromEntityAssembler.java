package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.WaterBatch;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.WaterBatchResource;

public final class WaterBatchResourceFromEntityAssembler {

    private WaterBatchResourceFromEntityAssembler() {
    }

    public static WaterBatchResource toResourceFromEntity(WaterBatch entity) {
        return new WaterBatchResource(
                entity.getId(),
                entity.getCertificationCode(),
                entity.getDestinationSectorId(),
                entity.getVolumeLiters(),
                entity.getDeliveryTimestamp(),
                entity.getStatus(),
                entity.getSource()
        );
    }
}
