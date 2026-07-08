package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateThresholdCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.AlertLevel;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateThresholdResource;

/**
 * Assembles a {@link CreateThresholdCommand} from a {@link CreateThresholdResource}.
 *
 * @since 1.0
 */
public final class CreateThresholdCommandFromResourceAssembler {

    private CreateThresholdCommandFromResourceAssembler() {
    }

    public static CreateThresholdCommand toCommandFromResource(CreateThresholdResource resource, Long deviceId) {
        return new CreateThresholdCommand(
                deviceId,
                resource.minValue(),
                resource.maxValue(),
                resource.unit(),
                parseAlertLevel(resource.alertLevel()));
    }

    private static AlertLevel parseAlertLevel(String value) {
        return switch (value == null ? "" : value.trim().toLowerCase()) {
            case "warning" -> AlertLevel.WARNING;
            case "critical" -> AlertLevel.CRITICAL;
            default -> throw new IllegalArgumentException("Invalid alert level: " + value);
        };
    }
}
