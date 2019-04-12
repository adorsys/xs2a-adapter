package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ConsentStatusResponse200;
import de.adorsys.xs2a.gateway.model.ais.ConsentStatusTO;
import de.adorsys.xs2a.gateway.service.ais.ConsentStatus;
import de.adorsys.xs2a.gateway.service.ais.ConsentStatusResponse;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsentStatusResponseMapperTest {
    private ConsentStatusResponseMapper consentStatusResponseMapper = Mappers.getMapper(ConsentStatusResponseMapper.class);

    @Test
    public void toConsentStatusResponse200() {
        ConsentStatusResponse200 consentStatusResponse200 = consentStatusResponseMapper.toConsentStatusResponse200(consentStatusResponse());
        assertThat(consentStatusResponse200.getConsentStatus()).isEqualTo(ConsentStatusTO.RECEIVED);
    }

    private ConsentStatusResponse consentStatusResponse() {
        ConsentStatusResponse consentStatusResponse = new ConsentStatusResponse();
        consentStatusResponse.setConsentStatus(ConsentStatus.RECEIVED);
        return consentStatusResponse;
    }
}