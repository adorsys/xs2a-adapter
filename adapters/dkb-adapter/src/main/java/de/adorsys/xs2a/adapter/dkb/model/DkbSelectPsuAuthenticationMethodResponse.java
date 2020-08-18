package de.adorsys.xs2a.adapter.dkb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.AuthenticationObject;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.ScaStatus;

import java.util.Map;

public class DkbSelectPsuAuthenticationMethodResponse {
    private ScaStatus scaStatus;
    private AuthenticationObject chosenScaMethod;
    private DkbChallengeData challengeData;
    @JsonProperty("_links")
    private Map<String, HrefType> links;
    private String psuMessage;

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    public AuthenticationObject getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(AuthenticationObject chosenScaMethod) {
        this.chosenScaMethod = chosenScaMethod;
    }

    public DkbChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(DkbChallengeData challengeData) {
        this.challengeData = challengeData;
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
