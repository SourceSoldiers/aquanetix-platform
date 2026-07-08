package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.UpdateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceStatus;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.UpdateDeviceResource;

public final class UpdateDeviceCommandFromResourceAssembler {

    private UpdateDeviceCommandFromResourceAssembler() {
    }

    public static UpdateDeviceCommand toCommandFromResource(UpdateDeviceResource resource, Long deviceId) {
        return new UpdateDeviceCommand(
                deviceId,
                parseDeviceStatus(resource.currentStatus()),
                resource.lastTelemetrySync(),
                resource.name(),
                resource.location(),
                resource.unit(),
                resource.currentValue()
        );
    }

    private static DeviceStatus parseDeviceStatus(String value) {
        return switch (value == null ? "" : value.trim().toLowerCase()) {
            case "normal" -> DeviceStatus.NORMAL;
            case "warning" -> DeviceStatus.WARNING;
            case "alert" -> DeviceStatus.ALERT;
            case "offline" -> DeviceStatus.OFFLINE;
            default -> throw new IllegalArgumentException("Invalid device status: " + value);
        };
    }
}
