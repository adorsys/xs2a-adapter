package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ChallengeDataTO;
import de.adorsys.xs2a.adapter.model.ConsentsResponse201TO;
import de.adorsys.xs2a.adapter.service.ais.ConsentStatus;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static de.adorsys.xs2a.adapter.TestModelBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsentCreationResponseMapperTest {

    @Test
    public void toConsentResponse201() {
        ConsentCreationResponseMapper mapper = Mappers.getMapper(ConsentCreationResponseMapper.class);

        ConsentsResponse201TO response201 = mapper.toConsentResponse201(buildConsentCreationResponse());

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
