package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.pis.ScaStatusTO;
import de.adorsys.xs2a.gateway.model.shared.ScaStatusResponse;
import de.adorsys.xs2a.gateway.service.PaymentInitiationScaStatusResponse;
import de.adorsys.xs2a.gateway.service.ScaStatus;

import java.util.Optional;

public class PaymentInitiationScaStatusResponseMapper {

    public ScaStatusResponse mapToScaStatusResponse(PaymentInitiationScaStatusResponse paymentInitiationScaStatusResponse) {
        return Optional.ofNullable(paymentInitiationScaStatusResponse)
                       .map(r -> {
                           ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
                           scaStatusResponse.setScaStatus(mapToScaStatusTO(r.getScaStatus()));
                           return scaStatusResponse;
                       })
                       .orElse(null);
    }

    private ScaStatusTO mapToScaStatusTO(ScaStatus scaStatus) {
        return ScaStatusTO.fromValue(scaStatus.getValue());
    }
}
