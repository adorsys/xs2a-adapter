package de.adorsys.xs2a.adapter.service.model;

import de.adorsys.xs2a.adapter.service.model.ScaStatus;

public class PaymentInitiationScaStatusResponse {
    private ScaStatus scaStatus;

    public PaymentInitiationScaStatusResponse(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    public ScaStatus getScaStatus() {
        return scaStatus;
    }
}
