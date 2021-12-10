package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AuthorisationConfirmationResponse {
    private ScaStatusAuthorisationConfirmation scaStatus;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    public ScaStatusAuthorisationConfirmation getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatusAuthorisationConfirmation scaStatus) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorisationConfirmationResponse that = (AuthorisationConfirmationResponse) o;
        return Objects.equals(scaStatus, that.scaStatus) &&
            Objects.equals(links, that.links) &&
            Objects.equals(psuMessage, that.psuMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scaStatus,
            links,
            psuMessage);
    }
}
