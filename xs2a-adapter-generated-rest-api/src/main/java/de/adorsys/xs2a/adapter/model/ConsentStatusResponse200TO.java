package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class ConsentStatusResponse200TO {
    private ConsentStatusTO consentStatus;

    private String psuMessage;

    public ConsentStatusTO getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatusTO consentStatus) {
        this.consentStatus = consentStatus;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}
