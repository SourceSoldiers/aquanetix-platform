package com.sourcesoldiers.aquanetix.platform.monitoring.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.monitoring.application.commandservices.AlertCommandService;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.ResolveAlertCommand; // ¡Aquí está el import que faltaba!
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.repositories.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository alertRepository;

    public AlertCommandServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(CreateAlertCommand command) {
        var alert = new Alert(command);
        var savedAlert = alertRepository.save(alert);
        return Optional.of(savedAlert);
    }

    @Override
    public Optional<Alert> handle(ResolveAlertCommand command) {
        // 1. Buscamos si la alerta existe
        var result = alertRepository.findById(command.alertId());

        // Si no existe, retornamos vacío
        if (result.isEmpty()) {
            return Optional.empty();
        }

        // 2. Extraemos la alerta, la resolvemos y la guardamos
        var alert = result.get();
        alert.resolve(); // ¡Esta es la acción pura de tu dominio!
        var savedAlert = alertRepository.save(alert);

        // 3. Retornamos la alerta actualizada
        return Optional.of(savedAlert);
    }
}