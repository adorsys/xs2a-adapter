package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.GrantTypeParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GrantTypeParamAdjusterTest {
    private static final String GRANT_TYPE_REQUIRED_VALUE = "authorization_code";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());

        GrantTypeParamAdjuster paramAdjuster = GrantTypeParamAdjuster.builder()
                                                   .grantTypeRequiredValue(GRANT_TYPE_REQUIRED_VALUE)
                                                   .constraint(ParamConstraint.REQUIRED)
                                                   .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.GRANT_TYPE))
            .isEqualTo(GRANT_TYPE_REQUIRED_VALUE);
    }

    @Test
    public void adjustParam_Success_GrantTypeIsAbsent_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());

        GrantTypeParamAdjuster paramAdjuster = GrantTypeParamAdjuster.builder()
                                                   .constraint(ParamConstraint.OPTIONAL)
                                                   .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_GrantTypeIsAbsent_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());

        GrantTypeParamAdjuster paramAdjuster = GrantTypeParamAdjuster.builder()
                                                   .constraint(ParamConstraint.REQUIRED)
                                                   .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.GRANT_TYPE);
    }
}
