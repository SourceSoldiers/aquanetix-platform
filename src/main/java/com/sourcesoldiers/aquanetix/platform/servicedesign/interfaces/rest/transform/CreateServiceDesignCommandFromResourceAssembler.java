package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.transform;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.commands.CreateServiceDesignCommand;
import com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources.CreateServiceDesignResource;

public class CreateServiceDesignCommandFromResourceAssembler {

    public static CreateServiceDesignCommand toCommandFromResource(CreateServiceDesignResource resource) {
        return new CreateServiceDesignCommand(
                resource.routeName(),
                resource.origin(),
                resource.destination(),
                resource.status()
        );
    }
}