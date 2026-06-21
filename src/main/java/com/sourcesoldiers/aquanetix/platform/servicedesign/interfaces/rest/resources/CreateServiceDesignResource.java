package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources;

public record CreateServiceDesignResource(
        String routeName,
        String origin,
        String destination,
        String status
) {}