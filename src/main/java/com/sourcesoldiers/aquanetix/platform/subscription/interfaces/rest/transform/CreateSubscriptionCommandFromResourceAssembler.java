package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands.CreateSubscriptionCommand;
import com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources.CreateSubscriptionResource;

public class CreateSubscriptionCommandFromResourceAssembler {

    public static CreateSubscriptionCommand toCommandFromResource(
            CreateSubscriptionResource resource) {

        return new CreateSubscriptionCommand(
                resource.userId(),
                resource.plan(),
                resource.status()
        );
    }
}