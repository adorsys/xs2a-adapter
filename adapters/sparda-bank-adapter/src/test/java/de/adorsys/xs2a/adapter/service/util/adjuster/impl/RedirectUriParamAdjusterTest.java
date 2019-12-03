package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RedirectUriParamAdjusterTest {
    private static final String REDIRECT_URI_FROM_TPP = "https://example.com";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setRedirectUri(REDIRECT_URI_FROM_TPP);

        RedirectUriParamAdjuster paramAdjuster = new RedirectUriParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.REDIRECT_URI)).isEqualTo(REDIRECT_URI_FROM_TPP);
    }

    @Test
    public void adjustParam_Failure_TppRedirectUriIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());

        RedirectUriParamAdjuster paramAdjuster = new RedirectUriParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.REDIRECT_URI);
    }
}
