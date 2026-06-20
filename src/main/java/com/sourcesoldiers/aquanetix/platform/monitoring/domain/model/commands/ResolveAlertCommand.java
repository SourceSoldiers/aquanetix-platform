package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands;

public record ResolveAlertCommand(Long alertId) {
    public ResolveAlertCommand {
        if (alertId == null || alertId <= 0) {
            throw new IllegalArgumentException("El ID de la alerta debe ser un número válido.");
        }
    }
}