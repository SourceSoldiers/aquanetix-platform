package com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.commands;

public record CreateServiceDesignCommand(
        String routeName,
        String origin,
        String destination,
        String status
) {}