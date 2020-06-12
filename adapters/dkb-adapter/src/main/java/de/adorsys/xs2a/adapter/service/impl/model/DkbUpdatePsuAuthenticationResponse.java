package de.adorsys.xs2a.adapter.service.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.service.model.AuthenticationObject;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.model.ScaStatus;

import java.util.List;
import java.util.Map;

public class DkbUpdatePsuAuthenticationResponse {
    private AuthenticationObject chosenScaMethod;
    private DkbChallengeData challengeData;
    private List<AuthenticationObject> scaMethods;
    @JsonProperty("_links")
    private Map<String, Link> links;
    private ScaStatus scaStatus;
    private String psuMessage;

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

    public List<AuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
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

