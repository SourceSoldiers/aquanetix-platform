package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.sourcesoldiers.aquanetix.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.CreateSubscriptionResource;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.SubscriptionResource;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.sourcesoldiers.aquanetix.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions")
public class SubscriptionsController {

    private final SubscriptionQueryService queryService;
    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionsController(
            SubscriptionQueryService queryService,
            SubscriptionCommandService subscriptionCommandService) {

        this.queryService = queryService;
        this.subscriptionCommandService = subscriptionCommandService;
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
    @PostMapping
    @Operation(summary = "Create subscription")
    public ResponseEntity<SubscriptionResource> createSubscription(
            @RequestBody CreateSubscriptionResource resource) {

        var command =
                CreateSubscriptionCommandFromResourceAssembler
                        .toCommandFromResource(resource);

        var subscription =
                subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var subscriptionResource =
                SubscriptionResourceFromEntityAssembler
                        .toResourceFromEntity(subscription.get());

        return new ResponseEntity<>(
                subscriptionResource,
                HttpStatus.CREATED);
    }
}