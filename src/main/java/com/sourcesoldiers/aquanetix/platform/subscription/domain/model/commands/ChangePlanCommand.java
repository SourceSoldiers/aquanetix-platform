package com.sourcesoldiers.aquanetix.platform.subscription.domain.model.commands;

public record ChangePlanCommand(Long subscriptionId, String newPlan) {
}
