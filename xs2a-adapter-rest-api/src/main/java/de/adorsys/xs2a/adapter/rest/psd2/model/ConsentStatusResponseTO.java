package de.adorsys.xs2a.adapter.rest.psd2.model;

public class ConsentStatusResponseTO {
    private String consentStatus;

    private String psuMessage;

    public String getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(String consentStatus) {
        this.consentStatus = consentStatus;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}
