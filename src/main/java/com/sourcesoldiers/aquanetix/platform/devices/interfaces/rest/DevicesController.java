package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.devices.application.commandservices.DeviceCommandService;
import com.sourcesoldiers.aquanetix.platform.devices.application.queryservices.DeviceQueryService;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetAllDevicesQuery;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetDeviceByIdQuery;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateDeviceResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateThresholdResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.DeviceResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.ThresholdResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.UpdateDeviceResource;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.DeviceResourceFromEntityAssembler;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform.ThresholdResourceFromEntityAssembler;
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


    @GetMapping
    @Operation(summary = "Get all devices", operationId = "GetAllDevices")
    @ApiResponse(responseCode = "200", description = "Devices retrieved",
            content = @Content(schema = @Schema(implementation = DeviceResource.class)))
    public ResponseEntity<List<DeviceResource>> getAllDevices() {
        var devices = deviceQueryService.handle(new GetAllDevicesQuery());
        var resources = devices.stream().map(DeviceResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }
    /**
     * Minimal error payload returned for failed operations.
     *
     * @param message human-readable description of the failure
     */
    private record ErrorBody(String message) {
    }
}
