package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.CodeVerifierParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeVerifierParamAdjusterTest {
    private static final String PROPERTY = "sparda.oauth_approach.default_code_verifier";
    private static final String VALID_CONFIG_FILE_PATH = "/external.adapter.config.properties";
    private static final String EMPTY_CONFIG_FILE_PATH = "/external.empty.adapter.config.properties";
    private static final String INVALID_CONFIG_FILE_PATH = "/external.invalid.adapter.config.properties";

    private static final String CODE_VERIFIER_FROM_TPP_VALID = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
    private static final String CODE_VERIFIER_FROM_TPP_INVALID = "zzz42";
    private static final String CODE_VERIFIER_FROM_CONFIG = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Test
    public void adjustParam_Success_TppCodeVerifierIsPresentAndValid() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeVerifier(CODE_VERIFIER_FROM_TPP_VALID);

        CodeVerifierParamAdjuster paramAdjuster = new CodeVerifierParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_VERIFIER)).isEqualTo(CODE_VERIFIER_FROM_TPP_VALID);
    }

    @Test
    public void adjustParam_Failure_TppCodeVerifierIsPresentAndInvalid() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setCodeVerifier(CODE_VERIFIER_FROM_TPP_INVALID);

        CodeVerifierParamAdjuster paramAdjuster = new CodeVerifierParamAdjuster();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_VERIFIER);
    }

    @Test
    public void adjustParam_Success_TppCodeVerifierIsAbsent_ConfigCodeVerifierIsPresentAndValid() {
        Parameters parameters = new Parameters(new HashMap<>());
        setExternalConfigFile(VALID_CONFIG_FILE_PATH);

        CodeVerifierParamAdjuster paramAdjuster = new CodeVerifierParamAdjuster(PROPERTY);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CODE_VERIFIER)).isEqualTo(CODE_VERIFIER_FROM_CONFIG);
    }

    @Test
    public void adjustParam_Failure_TppCodeVerifierIsAbsent_ConfigCodeVerifierIsPresentAndInvalid() {
        Parameters parameters = new Parameters(new HashMap<>());
        setExternalConfigFile(INVALID_CONFIG_FILE_PATH);

        CodeVerifierParamAdjuster paramAdjuster = new CodeVerifierParamAdjuster(PROPERTY);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_VERIFIER);
    }

    @Test
    public void adjustParam_Failure_TppCodeVerifierIsAbsent_ConfigCodeVerifierIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());
        setExternalConfigFile(EMPTY_CONFIG_FILE_PATH);

        CodeVerifierParamAdjuster paramAdjuster = new CodeVerifierParamAdjuster(PROPERTY);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CODE_VERIFIER);
    }

    private void setExternalConfigFile(String configFilePath) {
        String file = getClass().getResource(configFilePath).getFile();
        System.setProperty("adapter.config.file.path", file);
        AdapterConfig.reload();
    }
}
