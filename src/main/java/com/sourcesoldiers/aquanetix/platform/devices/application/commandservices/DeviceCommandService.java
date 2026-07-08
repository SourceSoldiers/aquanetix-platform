package com.sourcesoldiers.aquanetix.platform.devices.application.commandservices;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateThresholdCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.DeleteDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.UpdateDeviceCommand;
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

    Result<Device, String> handle(UpdateDeviceCommand command);

    Result<Boolean, String> handle(DeleteDeviceCommand command);

    /**
     * Creates a new threshold configuration for a device's sensor.
     *
     * @param command threshold creation data
     * @return the created threshold, or a failure message
     */
    Result<ThresholdConfiguration, String> handle(CreateThresholdCommand command);
}
