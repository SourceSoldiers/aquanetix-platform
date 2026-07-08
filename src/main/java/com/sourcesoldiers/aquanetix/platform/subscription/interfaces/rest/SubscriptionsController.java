package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.sourcesoldiers.aquanetix.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.ChangePlanCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.CreateSubscriptionResource;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.ChangePlanResource;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.PlanResource;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.SubscriptionResource;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.valueobjects.PlanCatalog;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform.PlanResourceFromDefinitionAssembler;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import com.sourcesoldiers.aquanetix.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.RenewSubscriptionCommand;
import java.util.List;
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
    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        var resources = PlanCatalog.ALL.stream()
                .map(PlanResourceFromDefinitionAssembler::toResource)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        var resources = queryService.handleAll().stream()
                .map(SubscriptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id) {

        Optional<Subscription> subscription =
                queryService.handle(id);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                SubscriptionResourceFromEntityAssembler
                        .toResourceFromEntity(subscription.get()));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getByUserId(
            @PathVariable Integer userId) {

        Optional<Subscription> subscription =
                queryService.handleByUserId(userId);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                SubscriptionResourceFromEntityAssembler
                        .toResourceFromEntity(subscription.get()));
    }
    @PostMapping
    public ResponseEntity<?> createSubscription(
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

        return ResponseEntity.ok(subscriptionResource);
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long id) {

        var command = new CancelSubscriptionCommand(id);

        var subscription =
                subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource =
                SubscriptionResourceFromEntityAssembler
                        .toResourceFromEntity(subscription.get());

        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}/renew")
    public ResponseEntity<?> renewSubscription(
            @PathVariable Long id) {

        var command = new RenewSubscriptionCommand(id);

        var subscription =
                subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource =
                SubscriptionResourceFromEntityAssembler
                        .toResourceFromEntity(subscription.get());

        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}/plan")
    public ResponseEntity<?> changePlan(
            @PathVariable Long id,
            @RequestBody ChangePlanResource resource) {

        var command = new ChangePlanCommand(id, resource.newPlan());

        var subscription =
                subscriptionCommandService.handle(command);

        if (subscription.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorBody("Subscription not found or invalid plan."));
        }

        var subscriptionResource =
                SubscriptionResourceFromEntityAssembler
                        .toResourceFromEntity(subscription.get());

        return ResponseEntity.ok(subscriptionResource);
    }

    private record ErrorBody(String message) {
    }
}
