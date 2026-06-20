package com.sourcesoldiers.aquanetix.platform.monitoring.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertByIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAllAlertsQuery;

import java.util.List;
import java.util.Optional;

public interface AlertQueryService {
    Optional<Alert> handle(GetAlertByIdQuery query);
    List<Alert> handle(GetAllAlertsQuery query);
}