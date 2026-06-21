package com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands;

public record CreateSubscriptionCommand(
        Integer userId,
        String plan,
        String status
) {
}