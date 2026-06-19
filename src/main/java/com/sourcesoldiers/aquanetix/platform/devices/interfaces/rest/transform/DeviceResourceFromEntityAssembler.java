package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
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
                entity.getDeviceType().name(),
                entity.getCurrentStatus().name(),
                entity.getLastTelemetrySync());
    }
}
