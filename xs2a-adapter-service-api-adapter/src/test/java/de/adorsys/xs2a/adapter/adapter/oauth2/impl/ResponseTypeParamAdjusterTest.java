package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.ResponseTypeParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTypeParamAdjusterTest {
    private static final String DEFAULT_RESPONSE_TYPE_PARAM_VALUE = "code";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());

        ResponseTypeParamAdjuster paramAdjuster = new ResponseTypeParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.RESPONSE_TYPE))
            .isEqualTo(DEFAULT_RESPONSE_TYPE_PARAM_VALUE);
    }
}
