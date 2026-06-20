package com.sourcesoldiers.aquanetix.platform.monitoring.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Getter
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Equivalente al AUTO_INCREMENT
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
    private LocalDateTime timestamp;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Double threshold;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


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
        this.timestamp = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    public void resolve() {
        this.status = "RESOLVED";
        this.updatedAt = LocalDateTime.now();
    }
}