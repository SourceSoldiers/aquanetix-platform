package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.aggregates.Subscription;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {

    public static SubscriptionResource toResourceFromEntity(
            Subscription entity) {

        return new SubscriptionResource(
                entity.getId(),
                entity.getUserId(),
                entity.getPlan(),
                entity.getStatus()
        );
    }
}