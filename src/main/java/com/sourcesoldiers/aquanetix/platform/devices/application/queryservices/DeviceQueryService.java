package com.sourcesoldiers.aquanetix.platform.devices.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetAllDevicesQuery;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.queries.GetDeviceByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service handling read operations (queries) for the Devices
 * bounded context.
 *
 * @since 1.0
 */
public interface DeviceQueryService {

    /**
     * Retrieves a single device by id.
     *
     * @param query query parameters
     * @return the device, when found
     */
    Optional<Device> handle(GetDeviceByIdQuery query);

    /**
     * Retrieves every registered device.
     *
     * @param query query parameters
     * @return all devices
     */
    List<Device> handle(GetAllDevicesQuery query);

}
