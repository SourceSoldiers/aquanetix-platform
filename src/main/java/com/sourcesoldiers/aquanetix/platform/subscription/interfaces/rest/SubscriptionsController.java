package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions")
public class SubscriptionsController {

    private final SubscriptionQueryService queryService;

    public SubscriptionsController(
            SubscriptionQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subscription by id")
    public ResponseEntity<Subscription> getById(
            @PathVariable Long id) {

        Optional<Subscription> subscription =
                queryService.handle(id);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subscription.get());
    }
}