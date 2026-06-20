package com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.ServiceDesign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDesignRepository extends JpaRepository<ServiceDesign, Long> {
}