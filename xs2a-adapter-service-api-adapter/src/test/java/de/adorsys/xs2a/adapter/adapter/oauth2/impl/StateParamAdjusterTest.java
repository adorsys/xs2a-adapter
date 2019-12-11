package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.StateParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class StateParamAdjusterTest {
    private static final String STATE_FROM_TPP = "State";

    @Test
    public void adjustParam_Success_TppStateIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setState(STATE_FROM_TPP);

        StateParamAdjuster paramAdjuster = StateParamAdjuster.builder()
                                               .constraint(ParamConstraint.REQUIRED)
                                               .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.STATE)).isEqualTo(STATE_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppStateIsAbsent_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());

        StateParamAdjuster paramAdjuster = StateParamAdjuster.builder()
                                               .constraint(ParamConstraint.OPTIONAL)
                                               .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_TppStateIsAbsent_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());

        StateParamAdjuster paramAdjuster = StateParamAdjuster.builder()
                                               .constraint(ParamConstraint.REQUIRED)
                                               .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.STATE);
    }
}
