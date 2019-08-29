package de.adorsys.xs2a.adapter.service.impl.model;

import de.adorsys.xs2a.adapter.service.ais.ConsentStatus;

public class UnicreditAccountScaStatusResponse {
    private ConsentStatus consentStatus;

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
    }
}
