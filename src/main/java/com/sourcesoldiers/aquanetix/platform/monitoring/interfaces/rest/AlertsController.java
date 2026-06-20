package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.monitoring.application.queryservices.AlertQueryService;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertByIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Available Alert endpoints")
public class AlertsController {

    private final AlertQueryService alertQueryService;

    public AlertsController(AlertQueryService alertQueryService) {
        this.alertQueryService = alertQueryService;
    }

    @GetMapping("/{alertId}")
    @Operation(summary = "Get alert by id")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long alertId) {
        GetAlertByIdQuery query = new GetAlertByIdQuery(alertId);
        Optional<Alert> alert = alertQueryService.handle(query);

        if (alert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AlertResource alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);
    }
}