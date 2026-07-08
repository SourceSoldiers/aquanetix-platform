package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.Destination;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories.DestinationRepository;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories.WaterBatchRepository;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.CreateDestinationResource;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.DestinationResource;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.transform.DestinationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Destinations", description = "Available Destination endpoints")
public class DestinationsController {

    private final DestinationRepository destinationRepository;
    private final WaterBatchRepository waterBatchRepository;

    public DestinationsController(DestinationRepository destinationRepository,
                                  WaterBatchRepository waterBatchRepository) {
        this.destinationRepository = destinationRepository;
        this.waterBatchRepository = waterBatchRepository;
    }

    @GetMapping
    @Operation(summary = "Get all destinations", operationId = "GetAllDestinations")
    @ApiResponse(responseCode = "200", description = "Destinations retrieved")
    public ResponseEntity<List<DestinationResource>> getAllDestinations() {
        var resources = destinationRepository.findAll().stream()
                .map(DestinationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{destinationId}")
    @Operation(summary = "Get destination by id", operationId = "GetDestinationById")
    @ApiResponse(responseCode = "200", description = "Destination found")
    @ApiResponse(responseCode = "404", description = "Destination not found")
    public ResponseEntity<?> getDestinationById(@PathVariable Long destinationId) {
        return destinationRepository.findById(destinationId)
                .map(DestinationResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a destination", operationId = "CreateDestination")
    @ApiResponse(responseCode = "201", description = "Destination created")
    @ApiResponse(responseCode = "409", description = "A destination with that name already exists")
    public ResponseEntity<?> createDestination(@Valid @RequestBody CreateDestinationResource resource) {
        if (destinationRepository.existsByNameIgnoreCase(resource.name())) {
            return ResponseEntity.status(409)
                    .body(new ErrorBody("A destination with that name already exists"));
        }

        var destination = new Destination(resource.name(), resource.address(), resource.description());
        var saved = destinationRepository.save(destination);
        return ResponseEntity.created(URI.create("/api/v1/destinations/" + saved.getId()))
                .body(DestinationResourceFromEntityAssembler.toResourceFromEntity(saved));
    }

    @DeleteMapping("/{destinationId}")
    @Operation(summary = "Delete a destination", operationId = "DeleteDestination")
    @ApiResponse(responseCode = "204", description = "Destination deleted")
    @ApiResponse(responseCode = "404", description = "Destination not found")
    @ApiResponse(responseCode = "409", description = "Destination is in use and cannot be deleted")
    public ResponseEntity<?> deleteDestination(@PathVariable Long destinationId) {
        if (!destinationRepository.existsById(destinationId)) {
            return ResponseEntity.notFound().build();
        }

        if (waterBatchRepository.existsByDestinationSectorId(destinationId)) {
            return ResponseEntity.status(409)
                    .body(new ErrorBody("Destination is in use and cannot be deleted"));
        }

        try {
            destinationRepository.deleteById(destinationId);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(409)
                    .body(new ErrorBody("Destination is in use and cannot be deleted"));
        }
    }

    private record ErrorBody(String message) {
    }
}
