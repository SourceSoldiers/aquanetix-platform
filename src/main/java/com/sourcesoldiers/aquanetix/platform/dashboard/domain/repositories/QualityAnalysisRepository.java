package com.sourcesoldiers.aquanetix.platform.dashboard.domain.repositories;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistence contract for the {@link QualityAnalysis} aggregate.
 *
 * @since 1.0
 */
@Repository
public interface QualityAnalysisRepository extends JpaRepository<QualityAnalysis, Long> {
}
