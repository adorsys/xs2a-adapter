package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.ClientIdParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientIdParamAdjusterTest {
    private static final String CLIENT_ID_FROM_TPP = "ID1";
    private static final String CLIENT_ID_FROM_CERTIFICATE = "ID2";
    private static final String CLIENT_ID_FROM_CONFIG = "ID3";

    @Test
    public void adjustParam_Success_TppClientIdIsPresent_CertificateClientIdIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setClientId(CLIENT_ID_FROM_TPP);

        ClientIdParamAdjuster paramAdjuster = ClientIdParamAdjuster.builder()
                                                  .clientIdFromCertificate(CLIENT_ID_FROM_CERTIFICATE)
                                                  .constraint(ParamConstraint.REQUIRED)
                                                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CLIENT_ID)).isEqualTo(CLIENT_ID_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppClientIdIsAbsent_CertificateClientIdIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());

        ClientIdParamAdjuster paramAdjuster = ClientIdParamAdjuster.builder()
                                                  .clientIdFromCertificate(CLIENT_ID_FROM_CERTIFICATE)
                                                  .constraint(ParamConstraint.REQUIRED)
                                                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CLIENT_ID)).isEqualTo(CLIENT_ID_FROM_CERTIFICATE);
    }

    @Test
    public void clientIdFromConfigPreferredOverClientIdFromCertificate() {
        ClientIdParamAdjuster paramAdjuster = ClientIdParamAdjuster.builder()
            .clientIdFromConfig(CLIENT_ID_FROM_CONFIG)
            .clientIdFromCertificate(CLIENT_ID_FROM_CERTIFICATE)
            .constraint(ParamConstraint.REQUIRED)
            .build();

        Map<String, String> parametersMap =
            paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), new Parameters(new HashMap<>()))
                .getParametersMap();

        assertThat(parametersMap.get(Parameters.CLIENT_ID)).isEqualTo(CLIENT_ID_FROM_CONFIG);
    }

    @Test
    public void adjustParam_Failure_TppClientIdIsPresent_CertificateClientIdIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setClientId(CLIENT_ID_FROM_TPP);

        ClientIdParamAdjuster paramAdjuster = ClientIdParamAdjuster.builder()
                                                  .constraint(ParamConstraint.REQUIRED)
                                                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CLIENT_ID)).isEqualTo(CLIENT_ID_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppClientIdIsPresent_CertificateClientIdIsAbsent_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());

        ClientIdParamAdjuster paramAdjuster = ClientIdParamAdjuster.builder()
                                                  .constraint(ParamConstraint.OPTIONAL)
                                                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_TppClientIdIsPresent_CertificateClientIdIsAbsent_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());

        ClientIdParamAdjuster paramAdjuster = ClientIdParamAdjuster.builder()
                                                  .constraint(ParamConstraint.REQUIRED)
                                                  .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.CLIENT_ID);
    }
}
