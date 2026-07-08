package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources;

import java.time.OffsetDateTime;

public record WaterBatchResource(
        Long id,
        String certificationCode,
        Long destinationSectorId,
        Double volumeLiters,
        OffsetDateTime deliveryTimestamp,
        String status,
        String source
) {
}
