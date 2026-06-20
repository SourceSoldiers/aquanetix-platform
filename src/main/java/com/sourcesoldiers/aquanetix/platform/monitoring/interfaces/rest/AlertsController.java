package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.monitoring.application.queryservices.AlertQueryService;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertByIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAllAlertsQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertsByDeviceIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Available Alert endpoints")
public class AlertsController {

    private final AlertQueryService alertQueryService;

    public AlertsController(AlertQueryService alertQueryService) {
        this.alertQueryService = alertQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all alerts")
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var getAllAlertsQuery = new GetAllAlertsQuery();
        var alerts = alertQueryService.handle(getAllAlertsQuery);

        var alertResources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(alertResources);
    }

    @GetMapping("/{alertId}")
    @Operation(summary = "Get alert by id")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long alertId) {
        var getAlertByIdQuery = new GetAlertByIdQuery(alertId);
        Optional<Alert> alert = alertQueryService.handle(getAlertByIdQuery);

        if (alert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);
    }

    @GetMapping("/device/{deviceId}")
    @Operation(summary = "Get alerts by device id")
    public ResponseEntity<List<AlertResource>> getAlertsByDeviceId(@PathVariable Long deviceId) {
        var getAlertsByDeviceIdQuery = new GetAlertsByDeviceIdQuery(deviceId);
        var alerts = alertQueryService.handle(getAlertsByDeviceIdQuery);

        var alertResources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(alertResources);
    }
}