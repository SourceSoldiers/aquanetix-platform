package com.sourcesoldiers.aquanetix.platform.monitoring.application.commandservices;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.ResolveAlertCommand;

import java.util.Optional;

public interface AlertCommandService {
    Optional<Alert> handle(CreateAlertCommand command);
    Optional<Alert> handle(ResolveAlertCommand command); // Añadir esta línea
}