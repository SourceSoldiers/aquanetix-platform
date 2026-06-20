package com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries;

/**
 * Query to retrieve a single quality analysis by its identifier.
 *
 * @param analysisId identifier of the quality analysis
 * @since 1.0
 */
public record GetQualityAnalysisByIdQuery(Long analysisId) {
}

