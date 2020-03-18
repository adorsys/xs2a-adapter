package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class AuthorisationConfirmationResponse {
    private ScaStatusAuthorisationConfirmation scaStatus;

    @JsonProperty("_links")
    private Map<String, Link> links;

    private String psuMessage;

    public ScaStatusAuthorisationConfirmation getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatusAuthorisationConfirmation scaStatus) {
        this.scaStatus = scaStatus;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}
