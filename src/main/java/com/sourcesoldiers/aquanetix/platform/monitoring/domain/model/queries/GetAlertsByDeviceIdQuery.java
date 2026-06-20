package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries;

public record GetAlertsByDeviceIdQuery(Long deviceId) {
    public GetAlertsByDeviceIdQuery {
        if (deviceId == null || deviceId <= 0) {
            throw new IllegalArgumentException("El ID del dispositivo debe ser un número válido.");
        }
    }
}