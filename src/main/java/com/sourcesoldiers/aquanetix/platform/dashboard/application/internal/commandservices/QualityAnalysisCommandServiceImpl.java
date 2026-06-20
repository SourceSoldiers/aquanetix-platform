package com.sourcesoldiers.aquanetix.platform.dashboard.application.internal.commandservices;

import com.sourcesoldiers.aquanetix.platform.dashboard.application.commandservices.QualityAnalysisCommandService;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.commands.CreateQualityAnalysisCommand;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.repositories.QualityAnalysisRepository;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link QualityAnalysisCommandService}.
 *
 * @since 1.0
 */
@Service
public class QualityAnalysisCommandServiceImpl implements QualityAnalysisCommandService {

    private final QualityAnalysisRepository qualityAnalysisRepository;

    public QualityAnalysisCommandServiceImpl(QualityAnalysisRepository qualityAnalysisRepository) {
        this.qualityAnalysisRepository = qualityAnalysisRepository;
    }

    @Override
    @Transactional
    public Result<QualityAnalysis, String> handle(CreateQualityAnalysisCommand command) {
        try {
            var analysis = new QualityAnalysis(
                    command.sensorSourceId(),
                    command.detectedParameters(),
                    command.severityScore());
            qualityAnalysisRepository.save(analysis);
            return Result.success(analysis);
        } catch (DataIntegrityViolationException ex) {
            return Result.failure("Invalid quality analysis data: " + ex.getMostSpecificCause().getMessage());
        } catch (Exception ex) {
            return Result.failure("Could not create the quality analysis: " + ex.getMessage());
        }
    }
}