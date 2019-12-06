package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BicParamAdjusterTest {
    private static final String BIC_FROM_TPP = "TESTBIC1XXX";
    private static final String BIC_FROM_ASPSP = "TESTBIC2XXX";

    @Test
    public void adjustParam_Success_TppBicIsPresent_AspspBicIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setBic(BIC_FROM_TPP);

        BicParamAdjuster paramAdjuster = new BicParamAdjuster(BIC_FROM_ASPSP);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.BIC)).isEqualTo(BIC_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppBicIsAbsent_AspspBicIsPresent() {
        Parameters parameters = new Parameters(new HashMap<>());

        BicParamAdjuster paramAdjuster = new BicParamAdjuster(BIC_FROM_ASPSP);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.BIC)).isEqualTo(BIC_FROM_ASPSP);
    }

    @Test
    public void adjustParam_Success_TppBicIsPresent_AspspBicIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setBic(BIC_FROM_TPP);

        BicParamAdjuster paramAdjuster = new BicParamAdjuster(null);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.BIC)).isEqualTo(BIC_FROM_TPP);
    }

    @Test
    public void adjustParam_Failure_TppBicIsAbsent_AspspBicIsAbsent() {
        Parameters parameters = new Parameters(new HashMap<>());

        BicParamAdjuster paramAdjuster = new BicParamAdjuster(null);

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.BIC);
    }
}
