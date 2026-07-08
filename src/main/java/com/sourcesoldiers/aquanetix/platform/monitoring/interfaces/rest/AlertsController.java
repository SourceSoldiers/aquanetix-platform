package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.monitoring.application.commandservices.AlertCommandService;
import com.sourcesoldiers.aquanetix.platform.monitoring.application.queryservices.AlertQueryService;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertByIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAllAlertsQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertsByDeviceIdQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.queries.GetAlertsByStatusQuery;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.AlertResource;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.CreateAlertResource;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.UpdateAlertResource;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform.UpdateAlertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    private final AlertCommandService alertCommandService;

    public AlertsController(AlertQueryService alertQueryService, AlertCommandService alertCommandService) {
        this.alertQueryService = alertQueryService;
        this.alertCommandService = alertCommandService;
    }

    @PostMapping
    @Operation(summary = "Create an alert", operationId = "CreateAlert")
    public ResponseEntity<AlertResource> createAlert(@Valid @RequestBody CreateAlertResource resource) {
        var createAlertCommand = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var alert = alertCommandService.handle(createAlertCommand);

        if (alert.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return new ResponseEntity<>(alertResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all alerts", operationId = "GetAllAlerts")
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var getAllAlertsQuery = new GetAllAlertsQuery();
        var alerts = alertQueryService.handle(getAllAlertsQuery);

        var alertResources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(alertResources);
    }

    @GetMapping("/{alertId}")
    @Operation(summary = "Get alert by id", operationId = "GetAlertById")
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
    @Operation(summary = "Get alerts by device id", operationId = "GetAlertsByDeviceId")
    public ResponseEntity<List<AlertResource>> getAlertsByDeviceId(@PathVariable Long deviceId) {
        var getAlertsByDeviceIdQuery = new GetAlertsByDeviceIdQuery(deviceId);
        var alerts = alertQueryService.handle(getAlertsByDeviceIdQuery);

        var alertResources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(alertResources);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get alerts by status", operationId = "GetAlertsByStatus")
    public ResponseEntity<List<AlertResource>> getAlertsByStatus(@PathVariable String status) {
        var getAlertsByStatusQuery = new GetAlertsByStatusQuery(status);
        var alerts = alertQueryService.handle(getAlertsByStatusQuery);

        var alertResources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(alertResources);
    }

    @PutMapping("/{alertId}")
    @Operation(summary = "Update an alert (resolve)", operationId = "UpdateAlert")
    public ResponseEntity<AlertResource> resolveAlert(@PathVariable Long alertId,
                                                      @RequestBody UpdateAlertResource resource) {
        var updateAlertCommand = UpdateAlertCommandFromResourceAssembler.toCommandFromResource(resource, alertId);
        var alert = alertCommandService.handle(updateAlertCommand);

        if (alert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);
    }
}
