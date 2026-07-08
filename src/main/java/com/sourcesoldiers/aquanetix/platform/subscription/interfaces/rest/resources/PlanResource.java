package com.sourcesoldiers.aquanetix.platform.subscription.interfaces.rest.resources;

import java.math.BigDecimal;

public record PlanResource(
        String name,
        BigDecimal monthlyCost,
        Integer maxDevices,
        Boolean isUnlimited
) {
}
