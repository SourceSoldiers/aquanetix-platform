package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.UpdateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceStatus;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.UpdateDeviceResource;

/**
 * Assembles an {@link UpdateDeviceCommand} from an {@link UpdateDeviceResource}.
 *
 * @since 1.0
 */
public final class UpdateDeviceCommandFromResourceAssembler {

    private UpdateDeviceCommandFromResourceAssembler() {
    }

    public static UpdateDeviceCommand toCommandFromResource(UpdateDeviceResource resource, Long deviceId) {
        return new UpdateDeviceCommand(
                deviceId,
                DeviceStatus.valueOf(resource.currentStatus().toUpperCase()),
                resource.lastTelemetrySync());
    }
}
