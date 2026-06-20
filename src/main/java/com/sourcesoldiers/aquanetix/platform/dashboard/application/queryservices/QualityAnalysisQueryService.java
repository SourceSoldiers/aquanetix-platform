package com.sourcesoldiers.aquanetix.platform.dashboard.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetQualityAnalysisByIdQuery;

import java.util.Optional;

/**
 * Application service handling read operations (queries) for the
 * Dashboard bounded context.
 *
 * @since 1.0
 */
public interface QualityAnalysisQueryService {

    /**
     * Retrieves a single quality analysis by id.
     *
     * @param query query parameters
     * @return the quality analysis, when found
     */
    Optional<QualityAnalysis> handle(GetQualityAnalysisByIdQuery query);
}
