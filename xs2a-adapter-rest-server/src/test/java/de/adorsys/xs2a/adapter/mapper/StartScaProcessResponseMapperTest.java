package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ScaStatusTO;
import de.adorsys.xs2a.adapter.model.StartScaprocessResponseTO;
import de.adorsys.xs2a.adapter.service.AuthenticationObject;
import de.adorsys.xs2a.adapter.service.ChallengeData;
import de.adorsys.xs2a.adapter.service.ScaStatus;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class StartScaProcessResponseMapperTest {
    private static final String AUTHORISATION_ID = "authorisation id";
    private static final String PSU_MESSAGE = "psu message";
    private StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);

    @Test
    public void toStartScaprocessResponseTO() {
        StartScaprocessResponseTO startScaprocessResponseTO =
                startScaProcessResponseMapper.toStartScaprocessResponseTO(startScaProcessResponse());
        assertThat(startScaprocessResponseTO.getScaStatus()).isEqualTo(ScaStatusTO.PSUAUTHENTICATED);
        assertThat(startScaprocessResponseTO.getAuthorisationId()).isEqualTo(AUTHORISATION_ID);
        assertThat(startScaprocessResponseTO.getScaMethods()).hasSize(2);
        assertThat(startScaprocessResponseTO.getChosenScaMethod()).isNotNull();
        assertThat(startScaprocessResponseTO.getChallengeData()).isNotNull();
        assertThat(startScaprocessResponseTO.getLinks()).isNotNull();
    }

    private StartScaProcessResponse startScaProcessResponse() {
        StartScaProcessResponse startScaProcessResponse = new StartScaProcessResponse();
        startScaProcessResponse.setScaStatus(ScaStatus.PSUAUTHENTICATED);
        startScaProcessResponse.setAuthorisationId(AUTHORISATION_ID);
        startScaProcessResponse.setScaMethods(Arrays.asList(new AuthenticationObject(), new AuthenticationObject()));
        startScaProcessResponse.setChosenScaMethod(new AuthenticationObject());
        startScaProcessResponse.setChallengeData(new ChallengeData());
        startScaProcessResponse.setLinks(Collections.emptyMap());
        startScaProcessResponse.setPsuMessage(PSU_MESSAGE);
        return startScaProcessResponse;
    }
}
