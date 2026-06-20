package com.sourcesoldiers.aquanetix.platform.devices.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.devices.application.commandservices.DeviceCommandService;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
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
}
