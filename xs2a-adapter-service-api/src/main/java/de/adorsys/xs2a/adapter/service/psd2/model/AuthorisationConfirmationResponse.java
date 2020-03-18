package de.adorsys.xs2a.adapter.service.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class AuthorisationConfirmationResponse {
    private String scaStatus;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    public String getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(String scaStatus) {
        this.scaStatus = scaStatus;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}
