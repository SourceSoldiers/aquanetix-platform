package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.devices.application.commandservices.DeviceCommandService;
import com.sourcesoldiers.aquanetix.platform.devices.application.queryservices.DeviceQueryService;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetAllDevicesQuery;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetDeviceByIdQuery;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetThresholdsByDeviceIdQuery;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateDeviceResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateThresholdResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.DeviceResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.ThresholdResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.UpdateDeviceResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.CreateDeviceCommandFromResourceAssembler;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.CreateThresholdCommandFromResourceAssembler;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.DeviceResourceFromEntityAssembler;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.ThresholdResourceFromEntityAssembler;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.UpdateDeviceCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * REST controller exposing the Devices bounded context endpoints:
 * device registration/lookup/update and threshold configuration management.
 *
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/devices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Devices", description = "Available Device endpoints")
public class DevicesController {

    private final DeviceQueryService deviceQueryService;
    private final DeviceCommandService deviceCommandService;

    public DevicesController(DeviceQueryService deviceQueryService, DeviceCommandService deviceCommandService) {
        this.deviceQueryService = deviceQueryService;
        this.deviceCommandService = deviceCommandService;
    }

    @PostMapping
    @Operation(summary = "Register a new device", operationId = "CreateDevice")
    @ApiResponse(responseCode = "201", description = "Device created",
            content = @Content(schema = @Schema(implementation = DeviceResource.class)))
    @ApiResponse(responseCode = "400", description = "Invalid device data")
    public ResponseEntity<?> createDevice(@Valid @RequestBody CreateDeviceResource resource) {
        var command = CreateDeviceCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = deviceCommandService.handle(command);
        if (result.isSuccess()) {
            var device = result.success().orElseThrow();
            var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device);
            return ResponseEntity.created(URI.create("/api/v1/devices/" + device.getId())).body(deviceResource);
        }
        var message = result.failure().orElseThrow();
        return ResponseEntity.badRequest().body(new ErrorBody(message));
    }

    @GetMapping
    @Operation(summary = "Get all devices", operationId = "GetAllDevices")
    @ApiResponse(responseCode = "200", description = "Devices retrieved",
            content = @Content(schema = @Schema(implementation = DeviceResource.class)))
    public ResponseEntity<List<DeviceResource>> getAllDevices() {
        var devices = deviceQueryService.handle(new GetAllDevicesQuery());
        var resources = devices.stream().map(DeviceResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{deviceId}")
    @Operation(summary = "Get device by id", operationId = "GetDeviceById")
    @ApiResponse(responseCode = "200", description = "Device found",
            content = @Content(schema = @Schema(implementation = DeviceResource.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    public ResponseEntity<?> getDeviceById(@PathVariable Long deviceId) {
        var device = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (device.isPresent()) {
            return ResponseEntity.ok(DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get()));
        }
        return ResponseEntity.status(404).body(new ErrorBody("Device with id " + deviceId + " not found"));
    }

    @PutMapping("/{deviceId}")
    @Operation(summary = "Update device monitoring status", operationId = "UpdateDevice")
    @ApiResponse(responseCode = "200", description = "Device updated",
            content = @Content(schema = @Schema(implementation = DeviceResource.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    public ResponseEntity<?> updateDevice(@PathVariable Long deviceId,
                                          @Valid @RequestBody UpdateDeviceResource resource) {
        var command = UpdateDeviceCommandFromResourceAssembler.toCommandFromResource(resource, deviceId);
        var result = deviceCommandService.handle(command);
        if (result.isSuccess()) {
            var device = result.success().orElseThrow();
            return ResponseEntity.ok(DeviceResourceFromEntityAssembler.toResourceFromEntity(device));
        }
        var message = result.failure().orElseThrow();
        return ResponseEntity.status(404).body(new ErrorBody(message));
    }

    @GetMapping("/{deviceId}/thresholds")
    @Operation(summary = "Get thresholds by device id", operationId = "GetThresholdsByDeviceId")
    @ApiResponse(responseCode = "200", description = "Thresholds retrieved",
            content = @Content(schema = @Schema(implementation = ThresholdResource.class)))
    public ResponseEntity<List<ThresholdResource>> getThresholdsByDeviceId(@PathVariable Long deviceId) {
        var thresholds = deviceQueryService.handle(new GetThresholdsByDeviceIdQuery(deviceId));
        var resources = thresholds.stream().map(ThresholdResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{deviceId}/thresholds")
    @Operation(summary = "Create a threshold for a device", operationId = "CreateThreshold")
    @ApiResponse(responseCode = "201", description = "Threshold created",
            content = @Content(schema = @Schema(implementation = ThresholdResource.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    public ResponseEntity<?> createThreshold(@PathVariable Long deviceId,
                                             @Valid @RequestBody CreateThresholdResource resource) {
        var command = CreateThresholdCommandFromResourceAssembler.toCommandFromResource(resource, deviceId);
        var result = deviceCommandService.handle(command);
        if (result.isSuccess()) {
            var threshold = result.success().orElseThrow();
            return ResponseEntity.created(URI.create("/api/v1/devices/" + deviceId + "/thresholds"))
                    .body(ThresholdResourceFromEntityAssembler.toResourceFromEntity(threshold));
        }
        var message = result.failure().orElseThrow();
        return ResponseEntity.status(404).body(new ErrorBody(message));
    }

    /**
     * Minimal error payload returned for failed operations.
     *
     * @param message human-readable description of the failure
     */
    private record ErrorBody(String message) {
    }
}
