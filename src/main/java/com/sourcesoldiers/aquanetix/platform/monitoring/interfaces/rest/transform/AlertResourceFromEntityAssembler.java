package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates.Alert;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {

    public static AlertResource toResourceFromEntity(Alert entity) {
        return new AlertResource(
                entity.getId(),
                entity.getDeviceId(),
                entity.getDeviceName(),
                entity.getLocation(),
                entity.getType(),
                entity.getSeverity(),
                entity.getMessage(),
                entity.getTimestamp().toString(),
                entity.getStatus(),
                entity.getValue(),
                entity.getThreshold()
        );
    }
}