package com.sourcesoldiers.aquanetix.platform.subscription.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.ChangePlanCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CreateSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.RenewSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.valueobjects.PlanCatalog;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import java.util.Optional;

@Service
public class SubscriptionCommandServiceImpl
        implements SubscriptionCommandService {

    private final SubscriptionRepository repository;

    public SubscriptionCommandServiceImpl(
            SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Subscription> handle(
            CreateSubscriptionCommand command) {

        if (!PlanCatalog.isValidPlan(command.plan())) {
            return Optional.empty();
        }

        var subscription = new Subscription(
                command.userId(),
                command.plan(),
                command.status()
        );

        repository.save(subscription);

        return Optional.of(subscription);
    }
    @Override
    public Optional<Subscription> handle(
            CancelSubscriptionCommand command) {

        var subscription =
                repository.findById(command.subscriptionId());

        if (subscription.isEmpty()) {
            return Optional.empty();
        }

        subscription.get().cancel();

        repository.save(subscription.get());

        return subscription;
    }

    @Override
    public Optional<Subscription> handle(
            RenewSubscriptionCommand command) {

        var subscription =
                repository.findById(command.subscriptionId());

        if (subscription.isEmpty()) {
            return Optional.empty();
        }

        subscription.get().renew();

        repository.save(subscription.get());

        return subscription;
    }

    @Override
    public Optional<Subscription> handle(
            ChangePlanCommand command) {

        if (!PlanCatalog.isValidPlan(command.newPlan())) {
            return Optional.empty();
        }

        var subscription =
                repository.findById(command.subscriptionId());

        if (subscription.isEmpty()) {
            return Optional.empty();
        }

        subscription.get().changePlan(command.newPlan());

        repository.save(subscription.get());

        return subscription;
    }
}
