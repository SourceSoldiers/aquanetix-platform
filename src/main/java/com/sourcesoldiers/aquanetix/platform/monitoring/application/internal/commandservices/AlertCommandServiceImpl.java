package com.sourcesoldiers.aquanetix.platform.monitoring.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.monitoring.application.commandservices.AlertCommandService;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.CreateAlertCommand;
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
}