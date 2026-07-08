package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceStatus;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceType;
import com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources.DeviceResource;

/**
 * Assembles a {@link DeviceResource} from a {@link Device} entity.
 *
 * @since 1.0
 */
public final class DeviceResourceFromEntityAssembler {

    private DeviceResourceFromEntityAssembler() {
    }

    public static DeviceResource toResourceFromEntity(Device entity) {
        return new DeviceResource(
                entity.getId(),
                entity.getOwnerId(),
                entity.getSerialNumber(),
                formatDeviceType(entity.getDeviceType()),
                formatDeviceStatus(entity.getCurrentStatus()),
                entity.getLastTelemetrySync(),
                entity.getName(),
                entity.getLocation(),
                entity.getUnit(),
                entity.getCurrentValue(),
                entity.getDestinationId());
    }

    private static String formatDeviceType(DeviceType type) {
        return switch (type) {
            case PH -> "PH";
            case TURBIDITY -> "Turbidity";
            case PRESSURE -> "Pressure";
            case LEVEL -> "Level";
            case CHLORINE -> "Chlorine";
            case FLOW -> "Flow";
        };
    }

    private static String formatDeviceStatus(DeviceStatus status) {
        return switch (status) {
            case NORMAL -> "Normal";
            case WARNING -> "Warning";
            case ALERT -> "Alert";
            case OFFLINE -> "Offline";
        };
    }
}
