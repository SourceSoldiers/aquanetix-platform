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
                DeviceType.valueOf(resource.deviceType().toUpperCase()));
    }
}
