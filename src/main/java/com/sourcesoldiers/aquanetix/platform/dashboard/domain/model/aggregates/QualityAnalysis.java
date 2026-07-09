package com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyStatus;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

/**
 * Aggregate root representing a water quality analysis performed
 * on telemetry data received from a sensor device.
 *
 * @since 1.0
 */
@Entity
@Table(name = "quality_analyses")
@NoArgsConstructor(force = true)
public class QualityAnalysis {
    private static final double MIN_SEVERITY = 0.0;
    private static final double MAX_SEVERITY = 10.0;
    private static final double CONTAMINATION_RISK_THRESHOLD = 8.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sensor_source_id", nullable = false)
    private Integer sensorSourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "detected_parameters", nullable = false)
    private AnomalyType detectedParameters;

    @Enumerated(EnumType.STRING)
    @Column(name = "anomaly_status", nullable = false)
    private AnomalyStatus anomalyStatus;

    @Column(name = "severity_score", nullable = false)
    private Double severityScore;

    @Column(name = "has_contamination_peak_prediction", nullable = false)
    private Boolean hasContaminationPeakPrediction;

    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public QualityAnalysis(Integer sensorSourceId, AnomalyType detectedParameters, Double severityScore) {
        this.sensorSourceId = sensorSourceId;
        this.detectedParameters = detectedParameters;
        this.severityScore = normalizeSeverity(severityScore);
        this.anomalyStatus = AnomalyStatus.DETECTED;
        this.hasContaminationPeakPrediction =
                this.severityScore >= CONTAMINATION_RISK_THRESHOLD;
    }

    public void evaluateAnomaly() { this.anomalyStatus = AnomalyStatus.EVALUATED; }
    public void confirmAnomaly() { this.anomalyStatus = AnomalyStatus.CONFIRMED; }
    public void dismissAnomaly() { this.anomalyStatus = AnomalyStatus.DISMISSED; }
    public void markContaminationPeakPrediction() { this.hasContaminationPeakPrediction = true; }

    public Long getId() { return id; }
    public Integer getSensorSourceId() { return sensorSourceId; }
    public AnomalyType getDetectedParameters() { return detectedParameters; }
    public AnomalyStatus getAnomalyStatus() { return anomalyStatus; }
    public Double getSeverityScore() { return normalizeSeverity(severityScore); }
    public Boolean getHasContaminationPeakPrediction() {
        return getSeverityScore() >= CONTAMINATION_RISK_THRESHOLD;
    }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }

    private static double normalizeSeverity(Double severity) {
        if (severity == null) {
            return MIN_SEVERITY;
        }
        return Math.max(MIN_SEVERITY, Math.min(MAX_SEVERITY, severity));
    }
}
