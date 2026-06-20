package com.sourcesoldiers.aquanetix.platform.dashboard.application.internal.queryservices;

import com.sourcesoldiers.aquanetix.platform.dashboard.application.queryservices.QualityAnalysisQueryService;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates.QualityAnalysis;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetAllQualityAnalysesQuery;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetQualityAnalysisByIdQuery;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.repositories.QualityAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link QualityAnalysisQueryService}.
 *
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class QualityAnalysisQueryServiceImpl implements QualityAnalysisQueryService {

    private final QualityAnalysisRepository qualityAnalysisRepository;

    public QualityAnalysisQueryServiceImpl(QualityAnalysisRepository qualityAnalysisRepository) {
        this.qualityAnalysisRepository = qualityAnalysisRepository;
    }

    @Override
    public Optional<QualityAnalysis> handle(GetQualityAnalysisByIdQuery query) {
        return qualityAnalysisRepository.findById(query.analysisId());
    }

    @Override
    public List<QualityAnalysis> handle(GetAllQualityAnalysesQuery query) {
        return qualityAnalysisRepository.findAll();
    }
}