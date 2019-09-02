package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.model.ScaStatus;
import de.adorsys.xs2a.adapter.service.model.ConsentStatus;
import de.adorsys.xs2a.adapter.service.model.ConsentStatusResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditAccountScaStatusResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditPaymentScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;

import java.util.Optional;

public class ScaStatusResponseMapper {
    private final ScaStatusMapper scaStatusMapper = new ScaStatusMapper();

    public ScaStatusResponse toScaStatusResponse(UnicreditAccountScaStatusResponse unicreditAccountScaStatusResponse) {
        ScaStatus scaStatus = scaStatusMapper.toScaStatus(unicreditAccountScaStatusResponse.getConsentStatus());

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }

    public ScaStatusResponse toScaStatusResponse(ConsentStatusResponse consentStatusResponse) {
        ConsentStatus consentStatus = Optional.ofNullable(consentStatusResponse)
                                          .map(ConsentStatusResponse::getConsentStatus)
                                          .orElse(null);

        ScaStatus scaStatus = scaStatusMapper.toScaStatus(consentStatus);

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }

    public ScaStatusResponse toScaStatusResponse(UnicreditPaymentScaStatusResponse unicreditPaymentScaStatusResponse) {
        ScaStatus scaStatus = scaStatusMapper.toScaStatus(unicreditPaymentScaStatusResponse.getTransactionStatus());

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }
}
