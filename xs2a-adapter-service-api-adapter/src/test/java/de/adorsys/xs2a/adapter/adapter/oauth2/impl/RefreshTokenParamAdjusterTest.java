package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.RefreshTokenParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RefreshTokenParamAdjusterTest {
    private static final String REFRESH_TOKEN_FROM_TPP = "someRefreshToken";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setRefreshToken(REFRESH_TOKEN_FROM_TPP);

        RefreshTokenParamAdjuster paramAdjuster = RefreshTokenParamAdjuster.builder()
                                                      .constraint(ParamConstraint.REQUIRED)
                                                      .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.REFRESH_TOKEN))
            .isEqualTo(REFRESH_TOKEN_FROM_TPP);
    }

    @Test
    public void adjustParam_Failure_TppCodeIsAbsent_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());

        RefreshTokenParamAdjuster paramAdjuster = RefreshTokenParamAdjuster.builder()
                                                      .constraint(ParamConstraint.OPTIONAL)
                                                      .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_TppCodeIsAbsent_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());

        RefreshTokenParamAdjuster paramAdjuster = RefreshTokenParamAdjuster.builder()
                                                      .constraint(ParamConstraint.REQUIRED)
                                                      .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.REFRESH_TOKEN);
    }
}
