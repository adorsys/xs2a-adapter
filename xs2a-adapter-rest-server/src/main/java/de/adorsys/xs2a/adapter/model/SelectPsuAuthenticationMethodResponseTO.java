package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.Map;

@Generated("xs2a-gateway-codegen")
public class SelectPsuAuthenticationMethodResponseTO {
  private AuthenticationObjectTO chosenScaMethod;

  private ChallengeDataTO challengeData;

  @JsonProperty("_links")
  private Map<String, HrefTypeTO> links;

  private ScaStatusTO scaStatus;

  private String psuMessage;

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

  public ScaStatusTO getScaStatus() {
    return scaStatus;
  }

  public void setScaStatus(ScaStatusTO scaStatus) {
    this.scaStatus = scaStatus;
  }

  public String getPsuMessage() {
    return psuMessage;
  }

  public void setPsuMessage(String psuMessage) {
    this.psuMessage = psuMessage;
  }
}
