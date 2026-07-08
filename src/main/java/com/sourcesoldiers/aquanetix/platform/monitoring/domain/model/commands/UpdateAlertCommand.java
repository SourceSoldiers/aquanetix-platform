package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands;

import java.time.OffsetDateTime;

public record UpdateAlertCommand(
        Long id,
        Long deviceId,
        String deviceName,
        String location,
        String type,
        String severity,
        String message,
        OffsetDateTime timestamp,
        String status,
        Double value,
        Double threshold
) {
    public UpdateAlertCommand {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la alerta debe ser un numero valido.");
        }
    }
}
