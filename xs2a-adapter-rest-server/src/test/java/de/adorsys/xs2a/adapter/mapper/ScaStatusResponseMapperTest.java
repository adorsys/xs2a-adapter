package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ScaStatusResponseTO;
import de.adorsys.xs2a.adapter.model.ScaStatusTO;
import de.adorsys.xs2a.adapter.service.ScaStatus;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ScaStatusResponseMapperTest {

    private final ScaStatusResponseMapper mapper = Mappers.getMapper(ScaStatusResponseMapper.class);

    @Test
    public void toScaStatusResponseTO() {
        ScaStatusResponseTO scaStatusResponseTO = mapper.toScaStatusResponseTO(scaStatusResponse());
        assertThat(scaStatusResponseTO.getScaStatus()).isEqualTo(ScaStatusTO.PSUAUTHENTICATED);
    }

    private ScaStatusResponse scaStatusResponse() {
        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(ScaStatus.PSUAUTHENTICATED);
        return scaStatusResponse;
    }
}
