package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.AlertLevel;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateThresholdResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateThresholdCommandFromResourceAssemblerTests {

    @Test
    void acceptsNormalAndEmptyAlertLevels() {
        var normal = CreateThresholdCommandFromResourceAssembler.toCommandFromResource(
                new CreateThresholdResource(0.0, 10.0, "pH", "Normal"), 1L);
        var empty = CreateThresholdCommandFromResourceAssembler.toCommandFromResource(
                new CreateThresholdResource(0.0, 10.0, "pH", ""), 1L);

        assertEquals(AlertLevel.NORMAL, normal.alertLevel());
        assertEquals(AlertLevel.NORMAL, empty.alertLevel());
    }
}
