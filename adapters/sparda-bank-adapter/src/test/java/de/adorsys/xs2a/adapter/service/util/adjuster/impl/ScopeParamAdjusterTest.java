package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ScopeParamAdjusterTest {
    private static final String SCOPE_FROM_TPP = "pis";
    private static final String DEFAULT_SCOPE_PARAM_VALUE = "ais";

    @Test
    public void adjustParam_Success_TppScopeIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScope(SCOPE_FROM_TPP);

        ScopeParamAdjuster paramAdjuster = new ScopeParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.SCOPE)).isEqualTo(SCOPE_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppScopeIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());

        ScopeParamAdjuster paramAdjuster = new ScopeParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.SCOPE)).isEqualTo(DEFAULT_SCOPE_PARAM_VALUE);
    }
}
