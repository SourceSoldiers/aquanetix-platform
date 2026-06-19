package com.sourcesoldiers.aquanetix.platform.devices.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Persistence contract for {@link ThresholdConfiguration} entities.
 *
 * @since 1.0
 */
@Repository
public interface ThresholdConfigurationRepository extends JpaRepository<ThresholdConfiguration, Long> {

    /**
     * Finds every threshold configuration that belongs to the given device.
     *
     * @param deviceId identifier of the owning device
     * @return thresholds configured for the device
     */
    List<ThresholdConfiguration> findByDeviceId(Long deviceId);
}
