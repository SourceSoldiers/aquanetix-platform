package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.UpdateAlertCommand;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.UpdateAlertResource;

public class UpdateAlertCommandFromResourceAssembler {
    public static UpdateAlertCommand toCommandFromResource(UpdateAlertResource resource, Long alertId) {
        return new UpdateAlertCommand(
                alertId,
                resource.deviceId(),
                resource.deviceName(),
                resource.location(),
                resource.type(),
                resource.severity(),
                resource.message(),
                resource.timestamp(),
                resource.status(),
                resource.value(),
                resource.threshold()
        );
    }
}
