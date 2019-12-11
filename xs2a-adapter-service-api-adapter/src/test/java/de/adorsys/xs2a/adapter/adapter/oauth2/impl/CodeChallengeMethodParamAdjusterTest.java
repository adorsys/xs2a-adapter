package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.CodeChallengeMethodParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeChallengeMethodParamAdjusterTest {
    private static final String CODE_CHALLENGE_METHOD_VALID = "S256";
    private static final String CODE_CHALLENGE_METHOD_NOT_VALID = "ZZZ";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD_VALID);

        CodeChallengeMethodParamAdjuster paramAdjuster = new CodeChallengeMethodParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_CHALLENGE_METHOD))
            .isEqualTo(CODE_CHALLENGE_METHOD_VALID);
    }

    @Test
    public void adjustParam_Failure_CodeChallengeMethodIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());

        CodeChallengeMethodParamAdjuster paramAdjuster = new CodeChallengeMethodParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_CodeChallengeMethodIsNotValid() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD_NOT_VALID);

        CodeChallengeMethodParamAdjuster paramAdjuster = new CodeChallengeMethodParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
    }
}
