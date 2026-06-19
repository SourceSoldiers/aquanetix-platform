package com.sourcesoldiers.aquanetix.platform.devices.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.devices.application.commandservices.DeviceCommandService;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.CreateThresholdCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.UpdateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import com.sourcesoldiers.aquanetix.platform.devices.domain.repositories.DeviceRepository;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link DeviceCommandService}.
 *
 * @since 1.0
 */
@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceRepository deviceRepository;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    @Transactional
    public Result<Device, String> handle(CreateDeviceCommand command) {
        try {
            var device = new Device(command.ownerId(), command.serialNumber(), command.deviceType());
            deviceRepository.save(device);
            return Result.success(device);
        } catch (DataIntegrityViolationException ex) {
            return Result.failure("Invalid device data: " + ex.getMostSpecificCause().getMessage());
        } catch (Exception ex) {
            return Result.failure("Could not create the device: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<Device, String> handle(UpdateDeviceCommand command) {
        var deviceOptional = deviceRepository.findById(command.id());
        if (deviceOptional.isEmpty()) {
            return Result.failure("Device with id " + command.id() + " was not found");
        }
        try {
            var device = deviceOptional.get();
            device.update(command);
            deviceRepository.save(device);
            return Result.success(device);
        } catch (Exception ex) {
            return Result.failure("Could not update the device: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public Result<ThresholdConfiguration, String> handle(CreateThresholdCommand command) {
        var deviceOptional = deviceRepository.findById(command.deviceId());
        if (deviceOptional.isEmpty()) {
            return Result.failure("Device with id " + command.deviceId() + " was not found");
        }
        try {
            var device = deviceOptional.get();
            var threshold = new ThresholdConfiguration(
                    command.deviceId().intValue(),
                    command.minValue(),
                    command.maxValue(),
                    command.unit(),
                    command.alertLevel());
            device.addThreshold(threshold);
            deviceRepository.save(device);
            return Result.success(threshold);
        } catch (Exception ex) {
            return Result.failure("Could not create the threshold: " + ex.getMessage());
        }
    }
}
