package com.sourcesoldiers.aquanetix.platform.devices.domain.model.aggregates;

import com.sourcesoldiers.aquanetix.platform.devices.domain.model.entities.ThresholdConfiguration;
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
