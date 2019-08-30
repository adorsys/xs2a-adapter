package de.adorsys.xs2a.adapter.service.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.service.model.AuthenticationObject;
import de.adorsys.xs2a.adapter.service.model.ConsentStatus;
import de.adorsys.xs2a.adapter.service.model.Link;

import java.util.List;
import java.util.Map;

public class UnicreditStartScaProcessResponse {
    private ConsentStatus consentStatus;
    private String authorisationId;
    private List<AuthenticationObject> scaMethods;
    private AuthenticationObject chosenScaMethod;
    private UnicreditChallengeData challengeData;
    @JsonProperty("_links")
    private Map<String, Link> links;
    private String psuMessage;

    @Override
    public String toString() {
        return "UnicreditStartScaProcessResponse{" +
                   "consentStatus=" + consentStatus +
                   ", authorisationId='" + authorisationId + '\'' +
                   ", scaMethods=" + scaMethods +
                   ", chosenScaMethod=" + chosenScaMethod +
                   ", challengeData=" + challengeData +
                   ", links=" + links +
                   ", psuMessage='" + psuMessage + '\'' +
                   '}';
    }

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
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

    public UnicreditChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(UnicreditChallengeData challengeData) {
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
