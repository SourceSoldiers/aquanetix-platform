package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates;

import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.CreateAlertCommand;
import com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.commands.UpdateAlertCommand;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "alert")
@Getter
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;

    @Column(name = "device_name", nullable = false, length = 100)
    private String deviceName;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false, length = 20)
    private String severity;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private OffsetDateTime timestamp;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "measured_value", nullable = false)
    private Double value;

    @Column(nullable = false)
    private Double threshold;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    protected Alert() {}

    public Alert(Long deviceId, String deviceName, String location, String type,
                 String severity, String message, Double value, Double threshold) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.location = location;
        this.type = type;
        this.severity = severity;
        this.message = message;
        this.value = value;
        this.threshold = threshold;

        this.status = "ACTIVE";
        this.timestamp = OffsetDateTime.now();
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }


    public Alert(CreateAlertCommand command) {
        this.deviceId = command.deviceId();
        this.deviceName = command.deviceName();
        this.location = command.location();
        this.type = command.type();
        this.severity = command.severity();
        this.message = command.message();
        this.timestamp = command.timestamp() == null ? OffsetDateTime.now() : command.timestamp();
        this.status = command.status() == null || command.status().isBlank() ? "Activa" : command.status();
        this.value = command.value();
        this.threshold = command.threshold();

        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public void resolve() {
        this.status = "Resuelta";
        this.updatedAt = OffsetDateTime.now();
    }

    public void update(UpdateAlertCommand command) {
        this.deviceId = command.deviceId();
        this.deviceName = command.deviceName();
        this.location = command.location();
        this.type = command.type();
        this.severity = command.severity();
        this.message = command.message();
        this.timestamp = command.timestamp();
        this.status = command.status();
        this.value = command.value();
        this.threshold = command.threshold();
        resolve();
    }
}
