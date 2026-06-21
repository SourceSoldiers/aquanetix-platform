package com.sourcesoldiers.aquanetix.platform.servicedesign.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.servicedesign.application.commandservices.ServiceDesignCommandService;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates.ServiceDesign;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.commands.CreateServiceDesignCommand;
import com.sourcesoldiers.aquanetix.platform.servicedesign.domain.repositories.ServiceDesignRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceDesignCommandServiceImpl implements ServiceDesignCommandService {

    private final ServiceDesignRepository serviceDesignRepository;

    public ServiceDesignCommandServiceImpl(ServiceDesignRepository serviceDesignRepository) {
        this.serviceDesignRepository = serviceDesignRepository;
    }

    @Override
    public Optional<ServiceDesign> handle(CreateServiceDesignCommand command) {
        var serviceDesign = new ServiceDesign(
                command.routeName(),
                command.origin(),
                command.destination(),
                command.status()
        );

        return Optional.of(serviceDesignRepository.save(serviceDesign));
    }
}