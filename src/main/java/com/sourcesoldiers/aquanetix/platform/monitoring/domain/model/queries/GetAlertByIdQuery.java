package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries;

public record GetAlertByIdQuery(Long alertId) {
    public GetAlertByIdQuery {
        if (alertId == null || alertId <= 0) {
            throw new IllegalArgumentException("El ID de la alerta debe ser un número positivo válido.");
        }
    }
}