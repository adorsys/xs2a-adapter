package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ConsentStatusResponse200TO;
import de.adorsys.xs2a.adapter.model.ConsentStatusTO;
import de.adorsys.xs2a.adapter.service.model.ConsentStatus;
import de.adorsys.xs2a.adapter.service.model.ConsentStatusResponse;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsentStatusResponseMapperTest {
    private ConsentStatusResponseMapper consentStatusResponseMapper = Mappers.getMapper(ConsentStatusResponseMapper.class);

    @Test
    public void toConsentStatusResponse200() {
        ConsentStatusResponse200TO consentStatusResponse200 = consentStatusResponseMapper.toConsentStatusResponse200(consentStatusResponse());
        assertThat(consentStatusResponse200.getConsentStatus()).isEqualTo(ConsentStatusTO.RECEIVED);
    }

    private ConsentStatusResponse consentStatusResponse() {
        ConsentStatusResponse consentStatusResponse = new ConsentStatusResponse();
        consentStatusResponse.setConsentStatus(ConsentStatus.RECEIVED);
        return consentStatusResponse;
    }
}
