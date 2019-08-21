package de.adorsys.xs2a.adapter.service.mapper;

import de.adorsys.xs2a.adapter.service.ScaStatus;
import de.adorsys.xs2a.adapter.service.ais.ConsentStatus;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.UnicreditScaStatusResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ScaStatusResponseMapper {

    default ScaStatusResponse toScaStatusResponse(UnicreditScaStatusResponse unicreditScaStatusResponse) {
        ConsentStatus consentStatus = unicreditScaStatusResponse.getConsentStatus();

        ScaStatus scaStatus;

        if (consentStatus == ConsentStatus.VALID) {
            scaStatus = ScaStatus.FINALISED;
        } else if (consentStatus == ConsentStatus.REJECTED) {
            scaStatus = ScaStatus.FAILED;
        } else {
            // TODO come up with better solution
            throw new RuntimeException();
        }

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }
}
