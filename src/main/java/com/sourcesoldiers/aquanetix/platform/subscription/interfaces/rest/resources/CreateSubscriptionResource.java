package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources;

public record CreateSubscriptionResource(
        Integer userId,
        String plan,
        String status
) {
}