package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands;

import java.time.OffsetDateTime;

public record CreateAlertCommand(
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
    public CreateAlertCommand {
        if (deviceId == null || deviceId <= 0) {
            throw new IllegalArgumentException("El ID del dispositivo es obligatorio.");
        }
        if (deviceName == null || deviceName.isBlank()) {
            throw new IllegalArgumentException("El nombre del dispositivo no puede estar vacío.");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje de la alerta es obligatorio.");
        }
    }
}
