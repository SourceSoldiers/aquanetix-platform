package com.sourcesoldiers.aquanetix.platform.devices.interfaces.rest.resources;

/**
 * Output representation of a {@code ThresholdConfiguration} exposed through
 * the REST API.
 *
 * @since 1.0
 */
public record ThresholdResource(
        Long id,
        Long deviceId,
        Double minValue,
        Double maxValue,
        String unit,
        String alertLevel) {
}
