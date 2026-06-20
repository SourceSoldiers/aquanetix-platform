package com.sourcesoldiers.aquanetix.platform.dashboard.application.commandservices;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.commands.CreateQualityAnalysisCommand;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.Result;

/**
 * Application service handling write operations for the Dashboard bounded context.
 *
 * @since 1.0
 */
public interface QualityAnalysisCommandService {

    Result<QualityAnalysis, String> handle(CreateQualityAnalysisCommand command);
}