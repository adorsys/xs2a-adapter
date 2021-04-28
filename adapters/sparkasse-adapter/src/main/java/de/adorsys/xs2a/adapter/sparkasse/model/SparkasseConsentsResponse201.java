package de.adorsys.xs2a.adapter.sparkasse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SparkasseConsentsResponse201 {
    private ConsentStatus consentStatus;

    private String consentId;

    private List<SparkasseAuthenticationObject> scaMethods;

    private SparkasseAuthenticationObject chosenScaMethod;

    private ChallengeData challengeData;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    public ConsentStatus getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatus consentStatus) {
        this.consentStatus = consentStatus;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public List<SparkasseAuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<SparkasseAuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public SparkasseAuthenticationObject getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(SparkasseAuthenticationObject chosenScaMethod) {
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
        SparkasseConsentsResponse201 that = (SparkasseConsentsResponse201) o;
        return Objects.equals(consentStatus, that.consentStatus) &&
            Objects.equals(consentId, that.consentId) &&
            Objects.equals(scaMethods, that.scaMethods) &&
            Objects.equals(chosenScaMethod, that.chosenScaMethod) &&
            Objects.equals(challengeData, that.challengeData) &&
            Objects.equals(links, that.links) &&
            Objects.equals(psuMessage, that.psuMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consentStatus,
            consentId,
            scaMethods,
            chosenScaMethod,
            challengeData,
            links,
            psuMessage);
    }
}
