package com.sourcesoldiers.aquanetix.platform.servicedesign.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "water_batches")
public class WaterBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String certificationCode;

    @Column(nullable = false)
    private Long destinationSectorId;

    @Column(nullable = false)
    private Double volumeLiters;

    @Column(nullable = false)
    private OffsetDateTime deliveryTimestamp;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 150)
    private String source;

    protected WaterBatch() {
    }

    public WaterBatch(String certificationCode,
                      Long destinationSectorId,
                      Double volumeLiters,
                      OffsetDateTime deliveryTimestamp,
                      String status,
                      String source) {
        this.certificationCode = certificationCode;
        this.destinationSectorId = destinationSectorId;
        this.volumeLiters = volumeLiters;
        this.deliveryTimestamp = deliveryTimestamp;
        this.status = status;
        this.source = source;
    }

    public void update(String certificationCode,
                       Long destinationSectorId,
                       Double volumeLiters,
                       OffsetDateTime deliveryTimestamp,
                       String status,
                       String source) {
        this.certificationCode = certificationCode;
        this.destinationSectorId = destinationSectorId;
        this.volumeLiters = volumeLiters;
        this.deliveryTimestamp = deliveryTimestamp;
        this.status = status;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public Long getDestinationSectorId() {
        return destinationSectorId;
    }

    public Double getVolumeLiters() {
        return volumeLiters;
    }

    public OffsetDateTime getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }
}
