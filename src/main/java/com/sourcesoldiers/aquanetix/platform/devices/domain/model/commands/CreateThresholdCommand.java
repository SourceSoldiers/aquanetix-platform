package com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.AlertLevel;

/**
 * Command to create a new threshold configuration for a device's sensor.
 *
 * @param deviceId   identifier of the device the threshold belongs to
 * @param minValue   lower bound of the accepted range
 * @param maxValue   upper bound of the accepted range
 * @param unit       unit of measurement (e.g. "pH", "NTU", "bar")
 * @param alertLevel severity to raise when the bound is exceeded
 * @since 1.0
 */
public record CreateThresholdCommand(Long deviceId, Double minValue, Double maxValue,
                                      String unit, AlertLevel alertLevel) {
}
