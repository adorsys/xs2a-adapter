package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.shared.ChallengeDataTO;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static de.adorsys.xs2a.gateway.TestModelBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ChallengeDataMapperTest {

    @Test
    public void toChallengeDataTO() {
        ChallengeDataMapper mapper = Mappers.getMapper(ChallengeDataMapper.class);
        ChallengeDataTO data = mapper.toChallengeDataTO(buildChallengeData());

        assertThat(data.getAdditionalInformation()).isEqualTo(ADDITIONAL_INFO);
        assertThat(data.getData()).isEqualTo(DATA);
        assertThat(data.getImageLink()).isEqualTo(LINK);
        assertThat(data.getOtpFormat().name()).isEqualTo("CHARACTERS");
        assertThat(data.getOtpMaxLength()).isEqualTo(LENGTH);
        assertThat(data.getImage()).isEqualTo(DATA.getBytes());
    }


}