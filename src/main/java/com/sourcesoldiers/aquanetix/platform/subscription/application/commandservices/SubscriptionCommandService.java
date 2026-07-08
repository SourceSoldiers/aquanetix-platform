package com.sourcesoldiers.aquanetix.platform.subscription.application.commandservices;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.ChangePlanCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CreateSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.RenewSubscriptionCommand;

import java.util.Optional;

public interface SubscriptionCommandService {

    Optional<Subscription> handle(
            CreateSubscriptionCommand command);
    Optional<Subscription> handle(
            CancelSubscriptionCommand command);

    Optional<Subscription> handle(
            RenewSubscriptionCommand command);

    Optional<Subscription> handle(
            ChangePlanCommand command);
}
