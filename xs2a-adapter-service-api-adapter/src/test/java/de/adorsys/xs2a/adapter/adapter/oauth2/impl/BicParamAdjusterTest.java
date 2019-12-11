package de.adorsys.xs2a.adapter.adapter.oauth2.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.BicParamAdjuster;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
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

        BicParamAdjuster paramAdjuster = BicParamAdjuster.builder()
                                             .bicFromAspsp(BIC_FROM_ASPSP)
                                             .constraint(ParamConstraint.REQUIRED)
                                             .build();

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

        BicParamAdjuster paramAdjuster = BicParamAdjuster.builder()
                                             .bicFromAspsp(BIC_FROM_ASPSP)
                                             .constraint(ParamConstraint.REQUIRED)
                                             .build();

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

        BicParamAdjuster paramAdjuster = BicParamAdjuster.builder()
                                             .constraint(ParamConstraint.REQUIRED)
                                             .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();

        Map<String, String> parametersMap = actual.getParametersMap();
        assertThat(parametersMap).hasSize(1);
        assertThat(parametersMap.get(Parameters.BIC)).isEqualTo(BIC_FROM_TPP);
    }

    @Test
    public void adjustParam_Success_TppBicIsAbsent_AspspBicIsAbsent_ParamOptional() {
        Parameters parameters = new Parameters(new HashMap<>());

        BicParamAdjuster paramAdjuster = BicParamAdjuster.builder()
                                             .constraint(ParamConstraint.OPTIONAL)
                                             .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isFalse();
        assertThat(actual.getParametersMap()).isEmpty();
        assertThat(actual.getMissingParameters()).isEmpty();
    }

    @Test
    public void adjustParam_Failure_TppBicIsAbsent_AspspBicIsAbsent_ParamRequired() {
        Parameters parameters = new Parameters(new HashMap<>());

        BicParamAdjuster paramAdjuster = BicParamAdjuster.builder()
                                             .constraint(ParamConstraint.REQUIRED)
                                             .build();

        ParamAdjustingResultHolder actual
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parameters);

        assertThat(actual.containsMissingParams()).isTrue();
        assertThat(actual.getParametersMap()).isEmpty();

        Set<String> missingParameters = actual.getMissingParameters();
        assertThat(missingParameters).hasSize(1);
        assertThat(missingParameters).contains(Parameters.BIC);
    }
}
