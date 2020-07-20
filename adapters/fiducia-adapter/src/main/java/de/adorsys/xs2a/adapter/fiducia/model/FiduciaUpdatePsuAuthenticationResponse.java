package de.adorsys.xs2a.adapter.fiducia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.AuthenticationObject;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.ScaStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FiduciaUpdatePsuAuthenticationResponse {
    private AuthenticationObject chosenScaMethod;

    private FiduciaChallengeData challengeData;

    private List<AuthenticationObject> scaMethods;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private ScaStatus scaStatus;

    private String psuMessage;

    private String authorisationId;

    public AuthenticationObject getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(AuthenticationObject chosenScaMethod) {
        this.chosenScaMethod = chosenScaMethod;
    }

    public FiduciaChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(FiduciaChallengeData challengeData) {
        this.challengeData = challengeData;
    }

    public List<AuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
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

    public String getAuthorisationId() {
        return authorisationId;
    }

    public void setAuthorisationId(String authorisationId) {
        this.authorisationId = authorisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaUpdatePsuAuthenticationResponse that = (FiduciaUpdatePsuAuthenticationResponse) o;
        return Objects.equals(chosenScaMethod, that.chosenScaMethod) &&
            Objects.equals(challengeData, that.challengeData) &&
            Objects.equals(scaMethods, that.scaMethods) &&
            Objects.equals(links, that.links) &&
            Objects.equals(scaStatus, that.scaStatus) &&
            Objects.equals(psuMessage, that.psuMessage) &&
            Objects.equals(authorisationId, that.authorisationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chosenScaMethod,
            challengeData,
            scaMethods,
            links,
            scaStatus,
            psuMessage,
            authorisationId);
    }
}
