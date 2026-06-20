package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries;

public record GetAlertsByStatusQuery(String status) {
    public GetAlertsByStatusQuery {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("El estado de la alerta no puede estar vacío.");
        }
    }
}