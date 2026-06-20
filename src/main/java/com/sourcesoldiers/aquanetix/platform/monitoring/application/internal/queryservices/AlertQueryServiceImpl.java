package com.sourcesoldiers.aquanetix.platform.monitoring.application.internal.queryservices;

import com.sourcesoldiers.aquanetix.platform.monitoring.application.queryservices.AlertQueryService;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertByIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAllAlertsQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertsByDeviceIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.repositories.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.alertId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public List<Alert> handle(GetAlertsByDeviceIdQuery query) {
        return alertRepository.findAllByDeviceId(query.deviceId());
    }
}