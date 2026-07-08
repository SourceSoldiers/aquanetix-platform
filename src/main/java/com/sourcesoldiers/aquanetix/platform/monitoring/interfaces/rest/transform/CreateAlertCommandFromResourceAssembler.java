package com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.sourcesoldiers.aquanetix.platform.monitoring.interfaces.rest.resources.CreateAlertResource;

public class CreateAlertCommandFromResourceAssembler {
    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource) {
        return new CreateAlertCommand(
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
