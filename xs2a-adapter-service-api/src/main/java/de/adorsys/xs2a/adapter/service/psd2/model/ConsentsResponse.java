package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsentsResponse {
    private String consentStatus;

    private String consentId;

    private List<AuthenticationObject> scaMethods;

    private AuthenticationObject chosenScaMethod;

    private ChallengeData challengeData;

    private Map<String, HrefType> links;

    private String message;

    public String getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(String consentStatus) {
        this.consentStatus = consentStatus;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
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

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    public void addLink(String name, HrefType link) {
        if (links == null) {
            links = new HashMap<>();
        }
        links.put(name, link);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
