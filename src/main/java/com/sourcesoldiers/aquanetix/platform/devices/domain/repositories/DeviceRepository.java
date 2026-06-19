package com.sourcesoldiers.aquanetix.platform.devices.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence contract for the {@link Device} aggregate. CRUD and basic
 * lookup operations are provided by {@link JpaRepository}.
 *
 * @since 1.0
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
