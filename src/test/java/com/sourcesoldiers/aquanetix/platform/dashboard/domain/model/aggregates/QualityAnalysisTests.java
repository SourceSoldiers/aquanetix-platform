package com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.aggregates;

import com.sourcesoldiers.aquanetix.platform.dashboard.domain.model.valueobjects.AnomalyType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QualityAnalysisTests {

    @Test
    void capsSeverityAtTenAndFlagsContaminationRisk() {
        var analysis = new QualityAnalysis(1, AnomalyType.PH, 11.5);

        assertEquals(10.0, analysis.getSeverityScore());
        assertTrue(analysis.getHasContaminationPeakPrediction());
    }

    @Test
    void clampsNegativeSeverityAndDoesNotFlagLowRisk() {
        var analysis = new QualityAnalysis(1, AnomalyType.TURBIDITY, -2.0);

        assertEquals(0.0, analysis.getSeverityScore());
        assertFalse(analysis.getHasContaminationPeakPrediction());
    }
}
