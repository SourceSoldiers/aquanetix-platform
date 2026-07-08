package com.sourcesoldiers.aquanetix.platform.subscription.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public final class PlanCatalog {

    public static final String BASIC = "Basic";
    public static final String SMART_CITY = "Smart City";
    public static final String INDUSTRIAL = "Industrial";

    public static final List<PlanDefinition> ALL = List.of(
            new PlanDefinition(BASIC, BigDecimal.valueOf(99), 10),
            new PlanDefinition(SMART_CITY, BigDecimal.valueOf(299), 35),
            new PlanDefinition(INDUSTRIAL, BigDecimal.valueOf(799), -1)
    );

    private PlanCatalog() {
    }

    public static Optional<PlanDefinition> findByName(String name) {
        return ALL.stream()
                .filter(plan -> plan.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public static boolean isValidPlan(String name) {
        return findByName(name).isPresent();
    }
}
