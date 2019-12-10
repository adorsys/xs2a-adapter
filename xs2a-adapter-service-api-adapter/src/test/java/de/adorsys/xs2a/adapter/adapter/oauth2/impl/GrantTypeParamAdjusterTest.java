package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.GrantTypeParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GrantTypeParamAdjusterTest {
    private static final String GRANT_TYPE_REQUIRED_VALUE = "authorization_code";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());

        GrantTypeParamAdjuster paramAdjuster
            = new GrantTypeParamAdjuster(GRANT_TYPE_REQUIRED_VALUE);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.GRANT_TYPE))
            .isEqualTo(GRANT_TYPE_REQUIRED_VALUE);
    }
}
