package com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest;

import com.sourcesoldiers.aquanetix.platform.dashboard.application.queryservices.QualityAnalysisQueryService;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetAllQualityAnalysesQuery;
import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.queries.GetQualityAnalysisByIdQuery;
import com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.resources.QualityAnalysisResource;
import com.sourcesoldiers.aquanetix.platform.dashboard.interfaces.rest.transform.QualityAnalysisResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing the Dashboard bounded context endpoints.
 *
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/quality-analyses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "QualityAnalysis", description = "Available Quality Analysis endpoints")
public class QualityAnalysisController {

    private final QualityAnalysisQueryService qualityAnalysisQueryService;

    public QualityAnalysisController(QualityAnalysisQueryService qualityAnalysisQueryService) {
        this.qualityAnalysisQueryService = qualityAnalysisQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all quality analyses", operationId = "GetAllQualityAnalyses")
    @ApiResponse(responseCode = "200", description = "Quality analyses retrieved",
            content = @Content(schema = @Schema(implementation = QualityAnalysisResource.class)))
    public ResponseEntity<List<QualityAnalysisResource>> getAllQualityAnalyses() {
        var analyses = qualityAnalysisQueryService.handle(new GetAllQualityAnalysesQuery());
        var resources = analyses.stream()
                .map(QualityAnalysisResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{analysisId}")
    @Operation(summary = "Get quality analysis by id", operationId = "GetQualityAnalysisById")
    @ApiResponse(responseCode = "200", description = "Quality analysis found",
            content = @Content(schema = @Schema(implementation = QualityAnalysisResource.class)))
    @ApiResponse(responseCode = "404", description = "Quality analysis not found")
    public ResponseEntity<?> getQualityAnalysisById(@PathVariable Long analysisId) {
        var analysis = qualityAnalysisQueryService.handle(new GetQualityAnalysisByIdQuery(analysisId));
        if (analysis.isPresent()) {
            return ResponseEntity.ok(
                    QualityAnalysisResourceFromEntityAssembler.toResourceFromEntity(analysis.get()));
        }
        return ResponseEntity.status(404).body(new ErrorBody("Quality analysis with id " + analysisId + " not found"));
    }

    private record ErrorBody(String message) {
    }
}