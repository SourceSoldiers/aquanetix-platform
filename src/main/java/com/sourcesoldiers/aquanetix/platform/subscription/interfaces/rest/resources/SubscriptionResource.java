package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources;

public record SubscriptionResource(
        Long id,
        Integer userId,
        String plan,
        String status
) {
}