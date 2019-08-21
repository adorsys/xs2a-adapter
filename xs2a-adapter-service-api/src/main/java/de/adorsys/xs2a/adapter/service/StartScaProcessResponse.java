package de.adorsys.xs2a.adapter.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.List;
import java.util.Map;

public class StartScaProcessResponse {
    private ScaStatus scaStatus;
    private String authorisationId;
    private List<AuthenticationObject> scaMethods;
    private AuthenticationObject chosenScaMethod;
    private ChallengeData challengeData;
    @JsonProperty("_links")
    private Map<String, Link> links;
    private String psuMessage;

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    public String getAuthorisationId() {
        return authorisationId;
    }

    public void setAuthorisationId(String authorisationId) {
        this.authorisationId = authorisationId;
    }

    public List<AuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

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

    public boolean isChosenScaMethodStage() {
        return chosenScaMethod != null;
    }

    public boolean isSelectScaMethodStage() {
        return scaMethods != null;
    }
}
