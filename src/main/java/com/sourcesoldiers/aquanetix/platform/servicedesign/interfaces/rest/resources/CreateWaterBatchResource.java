package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record CreateWaterBatchResource(
        @NotBlank String certificationCode,
        @NotNull Long destinationSectorId,
        @NotNull @Positive Double volumeLiters,
        @NotNull OffsetDateTime deliveryTimestamp,
        @NotBlank String status,
        @NotBlank String source
) {
}
