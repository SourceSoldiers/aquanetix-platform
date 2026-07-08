package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.WaterBatch;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories.DestinationRepository;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories.WaterBatchRepository;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.CreateWaterBatchResource;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.UpdateWaterBatchResource;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.WaterBatchResource;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.transform.WaterBatchResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/water-batches", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "WaterBatches", description = "Available Water Batch endpoints")
public class WaterBatchesController {

    private final WaterBatchRepository waterBatchRepository;
    private final DestinationRepository destinationRepository;

    public WaterBatchesController(WaterBatchRepository waterBatchRepository,
                                  DestinationRepository destinationRepository) {
        this.waterBatchRepository = waterBatchRepository;
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    @Operation(summary = "Get all water batches", operationId = "GetAllWaterBatches")
    @ApiResponse(responseCode = "200", description = "Water batches retrieved")
    public ResponseEntity<List<WaterBatchResource>> getAllWaterBatches() {
        var resources = waterBatchRepository.findAll().stream()
                .map(WaterBatchResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{waterBatchId}")
    @Operation(summary = "Get water batch by id", operationId = "GetWaterBatchById")
    @ApiResponse(responseCode = "200", description = "Water batch found")
    @ApiResponse(responseCode = "404", description = "Water batch not found")
    public ResponseEntity<?> getWaterBatchById(@PathVariable Long waterBatchId) {
        return waterBatchRepository.findById(waterBatchId)
                .map(WaterBatchResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a water batch", operationId = "CreateWaterBatch")
    @ApiResponse(responseCode = "201", description = "Water batch created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<?> createWaterBatch(@Valid @RequestBody CreateWaterBatchResource resource) {
        if (!destinationRepository.existsById(resource.destinationSectorId())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorBody("Destination with id " + resource.destinationSectorId() + " not found"));
        }

        var waterBatch = new WaterBatch(
                resource.certificationCode(),
                resource.destinationSectorId(),
                resource.volumeLiters(),
                resource.deliveryTimestamp(),
                resource.status(),
                resource.source()
        );

        var saved = waterBatchRepository.save(waterBatch);
        return ResponseEntity.created(URI.create("/api/v1/water-batches/" + saved.getId()))
                .body(WaterBatchResourceFromEntityAssembler.toResourceFromEntity(saved));
    }

    @PutMapping("/{waterBatchId}")
    @Operation(summary = "Update a water batch", operationId = "UpdateWaterBatch")
    @ApiResponse(responseCode = "200", description = "Water batch updated")
    @ApiResponse(responseCode = "404", description = "Water batch not found")
    public ResponseEntity<?> updateWaterBatch(@PathVariable Long waterBatchId,
                                              @Valid @RequestBody UpdateWaterBatchResource resource) {
        if (!destinationRepository.existsById(resource.destinationSectorId())) {
            return ResponseEntity.badRequest()
                    .body(new ErrorBody("Destination with id " + resource.destinationSectorId() + " not found"));
        }

        return waterBatchRepository.findById(waterBatchId)
                .map(waterBatch -> {
                    waterBatch.update(
                            resource.certificationCode(),
                            resource.destinationSectorId(),
                            resource.volumeLiters(),
                            resource.deliveryTimestamp(),
                            resource.status(),
                            resource.source()
                    );
                    var saved = waterBatchRepository.save(waterBatch);
                    return ResponseEntity.ok(WaterBatchResourceFromEntityAssembler.toResourceFromEntity(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{waterBatchId}")
    @Operation(summary = "Delete a water batch", operationId = "DeleteWaterBatch")
    @ApiResponse(responseCode = "204", description = "Water batch deleted")
    @ApiResponse(responseCode = "404", description = "Water batch not found")
    public ResponseEntity<?> deleteWaterBatch(@PathVariable Long waterBatchId) {
        if (!waterBatchRepository.existsById(waterBatchId)) {
            return ResponseEntity.notFound().build();
        }

        waterBatchRepository.deleteById(waterBatchId);
        return ResponseEntity.noContent().build();
    }

    private record ErrorBody(String message) {
    }
}
