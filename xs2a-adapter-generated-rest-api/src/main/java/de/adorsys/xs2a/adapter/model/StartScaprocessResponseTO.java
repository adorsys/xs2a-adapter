package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public class StartScaprocessResponseTO {
    private ScaStatusTO scaStatus;

    private String authorisationId;

    private List<AuthenticationObjectTO> scaMethods;

    private AuthenticationObjectTO chosenScaMethod;

    private ChallengeDataTO challengeData;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    private String psuMessage;

    public ScaStatusTO getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatusTO scaStatus) {
        this.scaStatus = scaStatus;
    }

    public String getAuthorisationId() {
        return authorisationId;
    }

    public void setAuthorisationId(String authorisationId) {
        this.authorisationId = authorisationId;
    }

    public List<AuthenticationObjectTO> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObjectTO> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public AuthenticationObjectTO getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(AuthenticationObjectTO chosenScaMethod) {
        this.chosenScaMethod = chosenScaMethod;
    }

    public ChallengeDataTO getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(ChallengeDataTO challengeData) {
        this.challengeData = challengeData;
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
