package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.CodeChallengeMethodParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeChallengeMethodParamAdjusterTest {
    private static final String CODE_CHALLENGE_METHOD_VALID = "S256";
    private static final String CODE_CHALLENGE_METHOD_NOT_VALID = "ZZZ";

    @Test
    public void adjustParam_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD_VALID);

        CodeChallengeMethodParamAdjuster paramAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .defaultCodeChallengeMethod(CODE_CHALLENGE_METHOD_VALID)
                  .supportedCodeChallengeMethods(new HashSet<>(Collections.singletonList(CODE_CHALLENGE_METHOD_VALID)))
                  .constraint(ParamConstraint.REQUIRED)
                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_CHALLENGE_METHOD))
            .isEqualTo(CODE_CHALLENGE_METHOD_VALID);
    }

    @Test
    public void adjustParam_Success_CodeChallengeMethodIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());

        CodeChallengeMethodParamAdjuster paramAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .defaultCodeChallengeMethod(CODE_CHALLENGE_METHOD_VALID)
                  .supportedCodeChallengeMethods(new HashSet<>(Collections.singletonList(CODE_CHALLENGE_METHOD_VALID)))
                  .constraint(ParamConstraint.REQUIRED)
                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_CHALLENGE_METHOD))
            .isEqualTo(CODE_CHALLENGE_METHOD_VALID);
    }

    @Test
    public void adjustParam_Success_CodeChallengeMethodIsAbsent_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());

        CodeChallengeMethodParamAdjuster paramAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .supportedCodeChallengeMethods(new HashSet<>(Collections.singletonList(CODE_CHALLENGE_METHOD_VALID)))
                  .constraint(ParamConstraint.OPTIONAL)
                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_CodeChallengeMethodIsAbsent_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());

        CodeChallengeMethodParamAdjuster paramAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .supportedCodeChallengeMethods(new HashSet<>(Collections.singletonList(CODE_CHALLENGE_METHOD_VALID)))
                  .constraint(ParamConstraint.REQUIRED)
                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_CHALLENGE_METHOD);
    }

    @Test
    public void adjustParam_Success_CodeChallengeMethodIsNotValid_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD_NOT_VALID);

        CodeChallengeMethodParamAdjuster paramAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .supportedCodeChallengeMethods(
                      new HashSet<>(Collections.singletonList(CODE_CHALLENGE_METHOD_VALID)))
                  .constraint(ParamConstraint.OPTIONAL)
                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_CodeChallengeMethodIsNotValid_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD_NOT_VALID);

        CodeChallengeMethodParamAdjuster paramAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .supportedCodeChallengeMethods(
                      new HashSet<>(Collections.singletonList(CODE_CHALLENGE_METHOD_VALID)))
                  .constraint(ParamConstraint.REQUIRED)
                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_CHALLENGE_METHOD);
    }
}
