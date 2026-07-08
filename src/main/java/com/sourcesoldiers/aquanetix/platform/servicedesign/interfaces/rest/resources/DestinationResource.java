package com.sourcesoldiers.aquanetix.platform.servicedesign.interfaces.rest.resources;

public record DestinationResource(
        Long id,
        String name,
        String address,
        String description
) {
}
