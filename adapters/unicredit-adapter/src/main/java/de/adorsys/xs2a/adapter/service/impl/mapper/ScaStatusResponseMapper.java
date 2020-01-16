package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.model.ScaStatus;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditPaymentScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;

public class ScaStatusResponseMapper {
    private final ScaStatusMapper scaStatusMapper = new ScaStatusMapper();

    public ScaStatusResponse toScaStatusResponse(UnicreditPaymentScaStatusResponse unicreditPaymentScaStatusResponse) {
        ScaStatus scaStatus = scaStatusMapper.toScaStatus(unicreditPaymentScaStatusResponse.getTransactionStatus());

        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(scaStatus);

        return scaStatusResponse;
    }
}
