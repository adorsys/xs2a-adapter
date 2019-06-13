package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.ScaStatusResponseTO;
import de.adorsys.xs2a.adapter.model.ScaStatusTO;
import de.adorsys.xs2a.adapter.service.PaymentInitiationScaStatusResponse;
import de.adorsys.xs2a.adapter.service.ScaStatus;

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
