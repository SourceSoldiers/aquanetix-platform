package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceType;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.CreateDeviceResource;

/**
 * Assembles a {@link CreateDeviceCommand} from a {@link CreateDeviceResource}.
 *
 * @since 1.0
 */
public final class CreateDeviceCommandFromResourceAssembler {

    private CreateDeviceCommandFromResourceAssembler() {
    }

    public static CreateDeviceCommand toCommandFromResource(CreateDeviceResource resource) {
        return new CreateDeviceCommand(
                resource.ownerId(),
                resource.serialNumber(),
                parseDeviceType(resource.deviceType()),
                resource.name(),
                resource.location(),
                resource.unit(),
                resource.currentValue(),
                resource.destinationId());
    }

    private static DeviceType parseDeviceType(String value) {
        return switch (value == null ? "" : value.trim().toLowerCase()) {
            case "ph" -> DeviceType.PH;
            case "turbidity" -> DeviceType.TURBIDITY;
            case "pressure" -> DeviceType.PRESSURE;
            case "level" -> DeviceType.LEVEL;
            case "chlorine" -> DeviceType.CHLORINE;
            case "flow" -> DeviceType.FLOW;
            default -> throw new IllegalArgumentException("Invalid device type: " + value);
        };
    }
}
