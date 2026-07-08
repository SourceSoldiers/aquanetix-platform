package com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.WaterBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterBatchRepository extends JpaRepository<WaterBatch, Long> {
    boolean existsByDestinationSectorId(Long destinationSectorId);
}
