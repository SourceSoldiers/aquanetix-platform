package com.sourcesoldiers.aquanetix.platform.monitoring.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Spring Boot genera el SQL automáticamente para buscar todos por ID de dispositivo
    List<Alert> findAllByDeviceId(Long deviceId);

    // Spring Boot genera el SQL automáticamente para buscar todos por su estado (ej: "ACTIVE")
    List<Alert> findAllByStatus(String status);
}