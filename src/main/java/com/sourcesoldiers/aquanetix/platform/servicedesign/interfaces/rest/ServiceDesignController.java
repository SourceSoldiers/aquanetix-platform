package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.servicedesign.application.commandservices.ServiceDesignCommandService;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.ServiceDesign;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories.ServiceDesignRepository;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.CreateServiceDesignResource;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.transform.CreateServiceDesignCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/water-batches") // Opcional: si también deseas actualizar la ruta de la API
@Tag(name = "WaterBatches", description = "WaterBatches")
public class ServiceDesignController {

    private final ServiceDesignRepository serviceDesignRepository;
    private final ServiceDesignCommandService serviceDesignCommandService;

    public ServiceDesignController(ServiceDesignRepository serviceDesignRepository,
                                   ServiceDesignCommandService serviceDesignCommandService) {
        this.serviceDesignRepository = serviceDesignRepository;
        this.serviceDesignCommandService = serviceDesignCommandService;
    }

    @GetMapping
    @Operation(summary = "Get all service designs")
    public ResponseEntity<?> getAllServiceDesigns() {
        return ResponseEntity.ok(serviceDesignRepository.findAll());
    }

    @GetMapping("/{serviceDesignId}")
    @Operation(summary = "Get service design by id")
    public ResponseEntity<?> getServiceDesignById(@PathVariable Long serviceDesignId) {
        Optional<ServiceDesign> serviceDesign = serviceDesignRepository.findById(serviceDesignId);

        if (!serviceDesign.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(serviceDesign.get());
    }

    @PostMapping
    @Operation(summary = "Create a service design")
    public ResponseEntity<?> createServiceDesign(@RequestBody CreateServiceDesignResource resource) {
        var command = CreateServiceDesignCommandFromResourceAssembler.toCommandFromResource(resource);
        Optional<ServiceDesign> serviceDesign = serviceDesignCommandService.handle(command);

        if (!serviceDesign.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(serviceDesign.get());
    }
}