package com.sourcesoldiers.aquanetix.platform.devices.application.commandservices;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.Result;

/**
 * Application service handling write operations (commands) for the Devices
 * bounded context.
 *
 * @since 1.0
 */
public interface DeviceCommandService {

    /**
     * Registers a new device.
     *
     * @param command device creation data
     * @return the created device, or a failure message
     */
    Result<Device, String> handle(CreateDeviceCommand command);

}
