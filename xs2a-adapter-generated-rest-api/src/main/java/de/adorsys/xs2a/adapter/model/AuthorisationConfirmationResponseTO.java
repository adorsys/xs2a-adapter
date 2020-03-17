package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public class AuthorisationConfirmationResponseTO {
    private ScaStatusAuthorisationConfirmationTO scaStatus;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    private String psuMessage;

    public ScaStatusAuthorisationConfirmationTO getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatusAuthorisationConfirmationTO scaStatus) {
        this.scaStatus = scaStatus;
    }

    public Map<String, HrefTypeTO> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefTypeTO> links) {
        this.links = links;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}
