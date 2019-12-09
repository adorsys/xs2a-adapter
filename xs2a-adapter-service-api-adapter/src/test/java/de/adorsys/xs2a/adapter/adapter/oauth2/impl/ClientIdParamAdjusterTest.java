package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.ClientIdParamAdjuster;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.model.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientIdParamAdjusterTest {
    private static final String CLIENT_ID_FROM_TPP = "ID1";
    private static final String CLIENT_ID_FROM_CERTIFICATE = "ID2";

    @Test
    public void adjustParam_Success_TppClientIdIsPresent_CertificateClientIdIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setClientId(CLIENT_ID_FROM_TPP);

        ClientIdParamAdjuster paramAdjuster = new ClientIdParamAdjuster(CLIENT_ID_FROM_CERTIFICATE);

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

        ClientIdParamAdjuster paramAdjuster = new ClientIdParamAdjuster(CLIENT_ID_FROM_CERTIFICATE);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CLIENT_ID)).isEqualTo(CLIENT_ID_FROM_CERTIFICATE);
    }

    @Test
    public void adjustParam_Success_TppClientIdIsPresent_CertificateClientIdIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setClientId(CLIENT_ID_FROM_TPP);

        ClientIdParamAdjuster paramAdjuster = new ClientIdParamAdjuster(null);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.CLIENT_ID)).isEqualTo(CLIENT_ID_FROM_TPP);
    }
}
