package com.sourcesoldiers.aquanetix.platform.servicedesign.application.commandservices;

import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.ServiceDesign;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.commands.CreateServiceDesignCommand;

import java.util.Optional;

public interface ServiceDesignCommandService {
    Optional<ServiceDesign> handle(CreateServiceDesignCommand command);
}