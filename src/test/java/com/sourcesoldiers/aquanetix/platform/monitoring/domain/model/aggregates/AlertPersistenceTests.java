package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.repositories.AlertRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:alert_persistence_test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
class AlertPersistenceTests {

    @Autowired
    private AlertRepository alertRepository;

    @Test
    void savesAnAlertUsingTheLegacyValueColumn() {
        var alert = new Alert(
                1L,
                "Turbidity device",
                "North reservoir",
                "Turbidity",
                "Critical",
                "Measured value exceeded the threshold.",
                9.5,
                5.0);

        var savedAlert = alertRepository.saveAndFlush(alert);

        assertNotNull(savedAlert.getId());
        assertEquals(9.5, alertRepository.findById(savedAlert.getId()).orElseThrow().getValue());
    }
}
