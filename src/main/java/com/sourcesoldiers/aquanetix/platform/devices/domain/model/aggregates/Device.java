package com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.commands.UpdateDeviceCommand;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceStatus;
import com.sourcesoldiers.aquanetix.platform.devices.domain.model.valueobjects.DeviceType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing a physical monitoring device (sensor) owned by
 * a user/account. Tracks its operational status and the threshold
 * configurations applied to its readings.
 *
 * @since 1.0
 */
@Entity
@Table(name = "device")
@NoArgsConstructor(force = true)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType deviceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false)
    private DeviceStatus currentStatus;

    @Column(name = "last_telemetry_sync", nullable = false)
    private OffsetDateTime lastTelemetrySync;

    @Column(name = "name", length = 100)
    private String name = "";

    @Column(name = "location", length = 150)
    private String location = "";

    @Column(name = "unit", length = 20)
    private String unit = "";

    @Column(name = "current_value")
    private Double currentValue = 0d;

    @Column(name = "destination_id")
    private Long destinationId;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThresholdConfiguration> thresholds = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public Device(Integer ownerId, String serialNumber, DeviceType deviceType) {
        this.ownerId = ownerId;
        this.serialNumber = serialNumber;
        this.deviceType = deviceType;
        this.currentStatus = DeviceStatus.NORMAL;
        this.lastTelemetrySync = OffsetDateTime.now();
    }

    public Device(Integer ownerId,
                  String serialNumber,
                  DeviceType deviceType,
                  String name,
                  String location,
                  String unit,
                  Double currentValue,
                  Long destinationId) {
        this(ownerId, serialNumber, deviceType);
        this.name = name != null ? name : "";
        this.location = location != null ? location : "";
        this.unit = unit != null ? unit : "";
        this.currentValue = currentValue != null ? currentValue : 0d;
        this.destinationId = destinationId;
    }

    public void updateStatus(DeviceStatus newStatus) {
        this.currentStatus = newStatus;
        this.lastTelemetrySync = OffsetDateTime.now();
    }

    public void goOffline() {
        this.currentStatus = DeviceStatus.OFFLINE;
    }

    public void addThreshold(ThresholdConfiguration threshold) {
        threshold.assignToDevice(this);
        this.thresholds.add(threshold);
    }

    public void update(UpdateDeviceCommand command) {
        this.currentStatus = command.currentStatus();
        this.lastTelemetrySync = command.lastTelemetrySync();
        if (command.name() != null) {
            this.name = command.name();
        }
        if (command.location() != null) {
            this.location = command.location();
        }
        if (command.unit() != null) {
            this.unit = command.unit();
        }
        if (command.currentValue() != null) {
            this.currentValue = command.currentValue();
        }
    }

    public Long getId() {
        return id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public DeviceStatus getCurrentStatus() {
        return currentStatus;
    }

    public OffsetDateTime getLastTelemetrySync() {
        return lastTelemetrySync;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getUnit() {
        return unit;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public List<ThresholdConfiguration> getThresholds() {
        return thresholds;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
