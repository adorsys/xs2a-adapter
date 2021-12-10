package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class UpdatePsuAuthenticationResponse {
  private Amount transactionFees;

  private Amount currencyConversionFees;

  private Amount estimatedTotalAmount;

  private Amount estimatedInterbankSettlementAmount;

  private Object chosenScaMethod;

  private ChallengeData challengeData;

  private List<AuthenticationObject> scaMethods;

  @JsonProperty("_links")
  private Map<String, HrefType> links;

  private ScaStatus scaStatus;

  private String psuMessage;

  private String authorisationId;

  public Amount getTransactionFees() {
    return transactionFees;
  }

  public void setTransactionFees(Amount transactionFees) {
    this.transactionFees = transactionFees;
  }

  public Amount getCurrencyConversionFees() {
    return currencyConversionFees;
  }

  public void setCurrencyConversionFees(Amount currencyConversionFees) {
    this.currencyConversionFees = currencyConversionFees;
  }

  public Amount getEstimatedTotalAmount() {
    return estimatedTotalAmount;
  }

  public void setEstimatedTotalAmount(Amount estimatedTotalAmount) {
    this.estimatedTotalAmount = estimatedTotalAmount;
  }

  public Amount getEstimatedInterbankSettlementAmount() {
    return estimatedInterbankSettlementAmount;
  }

  public void setEstimatedInterbankSettlementAmount(Amount estimatedInterbankSettlementAmount) {
    this.estimatedInterbankSettlementAmount = estimatedInterbankSettlementAmount;
  }

  public Object getChosenScaMethod() {
    return chosenScaMethod;
  }

  public void setChosenScaMethod(Object chosenScaMethod) {
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
    UpdatePsuAuthenticationResponse that = (UpdatePsuAuthenticationResponse) o;
    return Objects.equals(transactionFees, that.transactionFees) &&
        Objects.equals(currencyConversionFees, that.currencyConversionFees) &&
        Objects.equals(estimatedTotalAmount, that.estimatedTotalAmount) &&
        Objects.equals(estimatedInterbankSettlementAmount, that.estimatedInterbankSettlementAmount) &&
        Objects.equals(chosenScaMethod, that.chosenScaMethod) &&
        Objects.equals(challengeData, that.challengeData) &&
        Objects.equals(scaMethods, that.scaMethods) &&
        Objects.equals(links, that.links) &&
        Objects.equals(scaStatus, that.scaStatus) &&
        Objects.equals(psuMessage, that.psuMessage) &&
        Objects.equals(authorisationId, that.authorisationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionFees,
        currencyConversionFees,
        estimatedTotalAmount,
        estimatedInterbankSettlementAmount,
        chosenScaMethod,
        challengeData,
        scaMethods,
        links,
        scaStatus,
        psuMessage,
        authorisationId);
  }
}
