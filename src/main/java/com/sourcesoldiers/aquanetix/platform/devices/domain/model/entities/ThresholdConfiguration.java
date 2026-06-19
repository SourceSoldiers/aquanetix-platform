package com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates.Device;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.AlertLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * Threshold bounds configured for a given sensor of a {@code Device}. When
 * telemetry readings fall outside the {@code [minValue, maxValue]} range an
 * alert of the configured {@link AlertLevel} should be raised.
 *
 * @since 1.0
 */
@Entity
@Table(name = "threshold_configurations")
@NoArgsConstructor(force = true)
public class ThresholdConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sensor_id", nullable = false)
    private Integer sensorId;

    @Column(name = "min_value", nullable = false)
    private Double minValue;

    @Column(name = "max_value", nullable = false)
    private Double maxValue;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_level", nullable = false)
    private AlertLevel alertLevel;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    public ThresholdConfiguration(Integer sensorId, Double minValue, Double maxValue,
                                   String unit, AlertLevel alertLevel) {
        this.sensorId = sensorId;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.alertLevel = alertLevel;
    }

    public void update(Double minValue, Double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void assignToDevice(Device device) {
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public Integer getSensorId() {
        return sensorId;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public AlertLevel getAlertLevel() {
        return alertLevel;
    }

    public Device getDevice() {
        return device;
    }
}
