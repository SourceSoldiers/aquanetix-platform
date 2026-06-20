package com.sourcesoldiers.aquanetix.platform.dashboard.application.queryservices;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetAllQualityAnalysesQuery;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetQualityAnalysisByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service handling read operations (queries) for the
 * Dashboard bounded context.
 *
 * @since 1.0
 */
public interface QualityAnalysisQueryService {

    Optional<QualityAnalysis> handle(GetQualityAnalysisByIdQuery query);

    List<QualityAnalysis> handle(GetAllQualityAnalysesQuery query);
}