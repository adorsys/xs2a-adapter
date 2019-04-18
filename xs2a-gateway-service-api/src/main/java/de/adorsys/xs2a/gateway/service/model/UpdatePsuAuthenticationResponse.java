package de.adorsys.xs2a.gateway.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.gateway.service.AuthenticationObject;
import de.adorsys.xs2a.gateway.service.ChallengeData;
import de.adorsys.xs2a.gateway.service.ScaStatus;

import java.util.List;
import java.util.Map;

public class UpdatePsuAuthenticationResponse {
    private AuthenticationObject chosenScaMethod;
    private ChallengeData challengeData;
    private List<AuthenticationObject> scaMethods;
    @JsonProperty("_links")
    private Map links;
    private ScaStatus scaStatus;
    private String psuMessage;

    public AuthenticationObject getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(AuthenticationObject chosenScaMethod) {
        this.chosenScaMethod = chosenScaMethod;
    }

    public ChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(ChallengeData challengeData) {
        this.challengeData = challengeData;
    }

    public List<AuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public Map getLinks() {
        return links;
    }

    public void setLinks(Map links) {
        this.links = links;
    }

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}

