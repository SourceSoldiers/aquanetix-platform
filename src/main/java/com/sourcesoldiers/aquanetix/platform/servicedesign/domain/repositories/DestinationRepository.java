package com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    boolean existsByNameIgnoreCase(String name);
}
