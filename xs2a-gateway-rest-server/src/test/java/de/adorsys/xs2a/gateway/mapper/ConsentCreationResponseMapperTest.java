package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ConsentsResponse201;
import de.adorsys.xs2a.gateway.model.shared.ChallengeDataTO;
import de.adorsys.xs2a.gateway.service.ais.ConsentStatus;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static de.adorsys.xs2a.gateway.TestModelBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsentCreationResponseMapperTest {

    @Test
    public void toConsentResponse201() {
        ConsentCreationResponseMapper mapper = Mappers.getMapper(ConsentCreationResponseMapper.class);

        ConsentsResponse201 response201 = mapper.toConsentResponse201(buildConsentCreationResponse());

        assertThat(response201.getMessage()).isEqualTo(MESSAGE);
        assertThat(response201.getConsentId()).isEqualTo(CONSTENT_ID);
        assertThat(response201.getConsentStatus().name()).isEqualTo(ConsentStatus.RECEIVED.name());
        assertThat(response201.getLinks()).hasSize(1);
        ChallengeDataTO challengeData = response201.getChallengeData();
        assertThat(challengeData).isNotNull();
        assertThat(response201.getChosenScaMethod()).isNotNull();
        assertThat(response201.getScaMethods()).hasSize(1);
    }
}