package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import static org.assertj.core.api.Assertions.assertThat;

public class CodeChallengeParamAdjusterTest {
    private static final String VALID_CONFIG_FILE_PATH = "/external.adapter.config.properties";
    private static final String EMPTY_CONFIG_FILE_PATH = "/external.empty.adapter.config.properties";
    private static final String INVALID_CONFIG_FILE_PATH = "/external.invalid.adapter.config.properties";

    private static final String CODE_CHALLENGE_FROM_TPP = "testCodeChallenge";
    private static final String CODE_VERIFIER_FROM_TPP_VALID = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
    private static final String CODE_CHALLENGE_FOR_TPP_CODE_VERIFIER = "rSh9QTmLK8aq40PSR2e97ZeVuILzgrWr9ICm/Au9398=";
    private static final String CODE_CHALLENGE_FOR_CONFIG_CODE_VERIFIER = "v8X+DjYBUsqYxQ+rTtfjB4wX3rwpF3QNUACRO2hsoSk=";
    private static final String CODE_VERIFIER_FROM_TPP_INVALID = "zzz42";

    @Test
    public void adjustParam_Success_TppCodeChallengeIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeChallenge(CODE_CHALLENGE_FROM_TPP);

        CodeChallengeParamAdjuster paramAdjuster = new CodeChallengeParamAdjuster();

        ParamAdjustingResultHolder actual = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_CHALLENGE)).isEqualTo(CODE_CHALLENGE_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppCodeChallengeIsAbsent_TppCodeVerifierIsPresentAndValid() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeVerifier(CODE_VERIFIER_FROM_TPP_VALID);

        CodeChallengeParamAdjuster paramAdjuster = new CodeChallengeParamAdjuster();

        ParamAdjustingResultHolder actual = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_CHALLENGE)).isEqualTo(CODE_CHALLENGE_FOR_TPP_CODE_VERIFIER);
    }

    @Test
    public void adjustParam_Failure_TppCodeChallengeIsAbsent_TppCodeVerifierIsPresentAndInvalid() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeVerifier(CODE_VERIFIER_FROM_TPP_INVALID);

        CodeChallengeParamAdjuster paramAdjuster = new CodeChallengeParamAdjuster();

        ParamAdjustingResultHolder actual = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_CHALLENGE);
    }

    @Test
    public void adjustParam_Success_TppCodeChallengeIsAbsent_TppCodeVerifierIsAbsent_ConfigCodeVerifierIsPresentAndValid() {
        Parameters parameters = new Parameters(new HashMap<>());
        setExternalConfigFile(VALID_CONFIG_FILE_PATH);

        CodeChallengeParamAdjuster paramAdjuster = new CodeChallengeParamAdjuster();

        ParamAdjustingResultHolder actual = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_CHALLENGE)).isEqualTo(CODE_CHALLENGE_FOR_CONFIG_CODE_VERIFIER);
    }

    @Test
    public void adjustParam_Failure_TppCodeChallengeIsAbsent_TppCodeVerifierIsAbsent_ConfigCodeVerifierIsPresentAndInvalid() {
        Parameters parameters = new Parameters(new HashMap<>());
        setExternalConfigFile(INVALID_CONFIG_FILE_PATH);

        CodeChallengeParamAdjuster paramAdjuster = new CodeChallengeParamAdjuster();

        ParamAdjustingResultHolder actual = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_CHALLENGE);
    }

    @Test
    public void adjustParam_Failure_TppCodeChallengeIsAbsent_TppCodeVerifierIsAbsent_ConfigCodeVerifierIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());
        setExternalConfigFile(EMPTY_CONFIG_FILE_PATH);

        CodeChallengeParamAdjuster paramAdjuster = new CodeChallengeParamAdjuster();

        ParamAdjustingResultHolder actual = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_CHALLENGE);
    }

    private void setExternalConfigFile(String configFilePath) {
        String file = getClass().getResource(configFilePath).getFile();
        System.setProperty("adapter.config.file.path", file);
        AdapterConfig.reload();
    }
}
