package com.sourcesoldiers.aquanetix.platform.devices.application.internal.queryservices;

import com.sourcesoldiers.aquanetix.platform.devices.application.queryservices.DeviceQueryService;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetAllDevicesQuery;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetDeviceByIdQuery;
import com.sourcesoldiers.aquanetix.platform.devices.domain.repositories.DeviceRepository;
import com.sourcesoldiers.aquanetix.platform.devices.domain.repositories.ThresholdConfigurationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link DeviceQueryService}.
 *
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class DeviceQueryServiceImpl implements DeviceQueryService {

    private final DeviceRepository deviceRepository;
    private final ThresholdConfigurationRepository thresholdConfigurationRepository;

    public DeviceQueryServiceImpl(DeviceRepository deviceRepository,
                                   ThresholdConfigurationRepository thresholdConfigurationRepository) {
        this.deviceRepository = deviceRepository;
        this.thresholdConfigurationRepository = thresholdConfigurationRepository;
    }

    @Override
    public Optional<Device> handle(GetDeviceByIdQuery query) {
        return deviceRepository.findById(query.deviceId());
    }

    @Override
    public List<Device> handle(GetAllDevicesQuery query) {
        return deviceRepository.findAll();
    }

}
