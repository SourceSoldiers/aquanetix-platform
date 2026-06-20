package com.sourcesoldiers.aquanetix.platform.monitoring.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {


    List<Alert> findAllByDeviceId(Long deviceId);

    List<Alert> findAllByStatus(String status);
}