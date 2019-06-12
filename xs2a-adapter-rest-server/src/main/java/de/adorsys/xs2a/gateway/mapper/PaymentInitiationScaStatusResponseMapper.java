package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ScaStatusResponseTO;
import de.adorsys.xs2a.gateway.model.ScaStatusTO;
import de.adorsys.xs2a.gateway.service.PaymentInitiationScaStatusResponse;
import de.adorsys.xs2a.gateway.service.ScaStatus;

import java.util.Optional;

public class PaymentInitiationScaStatusResponseMapper {

    public ScaStatusResponseTO mapToScaStatusResponse(PaymentInitiationScaStatusResponse paymentInitiationScaStatusResponse) {
        return Optional.ofNullable(paymentInitiationScaStatusResponse)
                       .map(r -> {
                           ScaStatusResponseTO scaStatusResponse = new ScaStatusResponseTO();
                           scaStatusResponse.setScaStatus(mapToScaStatusTO(r.getScaStatus()));
                           return scaStatusResponse;
                       })
                       .orElse(null);
    }

    private ScaStatusTO mapToScaStatusTO(ScaStatus scaStatus) {
        return ScaStatusTO.fromValue(scaStatus.getValue());
    }
}
