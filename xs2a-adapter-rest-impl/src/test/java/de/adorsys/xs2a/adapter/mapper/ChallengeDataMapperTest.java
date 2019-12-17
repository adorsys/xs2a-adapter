package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.TestModelBuilder;
import de.adorsys.xs2a.adapter.model.ChallengeDataTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ChallengeDataMapperTest {

    @Test
    public void toChallengeDataTO() {
        ChallengeDataMapper mapper = Mappers.getMapper(ChallengeDataMapper.class);
        ChallengeDataTO data = mapper.toChallengeDataTO(TestModelBuilder.buildChallengeData());

        assertThat(data.getAdditionalInformation()).isEqualTo(TestModelBuilder.ADDITIONAL_INFO);
        assertThat(data.getData()).isEqualTo(TestModelBuilder.DATA);
        assertThat(data.getImageLink()).isEqualTo(TestModelBuilder.LINK);
        assertThat(data.getOtpFormat().name()).isEqualTo("CHARACTERS");
        assertThat(data.getOtpMaxLength()).isEqualTo(TestModelBuilder.LENGTH);
        assertThat(data.getImage()).isEqualTo(TestModelBuilder.IMAGE.getBytes());
    }


}
