package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.AlertLevel;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.ThresholdResource;

/**
 * Assembles a {@link ThresholdResource} from a {@link ThresholdConfiguration} entity.
 *
 * @since 1.0
 */
public final class ThresholdResourceFromEntityAssembler {

    private ThresholdResourceFromEntityAssembler() {
    }

    public static ThresholdResource toResourceFromEntity(ThresholdConfiguration entity) {
        return new ThresholdResource(
                entity.getId(),
                entity.getDevice() != null ? entity.getDevice().getId() : null,
                entity.getMinValue(),
                entity.getMaxValue(),
                entity.getUnit(),
                formatAlertLevel(entity.getAlertLevel()));
    }

    private static String formatAlertLevel(AlertLevel level) {
        return switch (level) {
            case WARNING -> "Warning";
            case CRITICAL -> "Critical";
        };
    }
}
