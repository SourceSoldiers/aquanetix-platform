package com.sourcesoldiers.aquanetix.platform.subscription.domain.model.valueobjects;

import java.math.BigDecimal;

public record PlanDefinition(String name, BigDecimal monthlyCost, Integer maxDevices) {
    public Boolean isUnlimited() {
        return maxDevices == -1;
    }
}
