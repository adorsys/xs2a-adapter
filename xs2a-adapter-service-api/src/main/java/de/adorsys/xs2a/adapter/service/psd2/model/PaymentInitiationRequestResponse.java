package de.adorsys.xs2a.adapter.service.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class PaymentInitiationRequestResponse {
    private String transactionStatus;

    private String paymentId;

    private Amount transactionFees;

    private Boolean transactionFeeIndicator;

    private List<AuthenticationObject> scaMethods;

    private AuthenticationObject chosenScaMethod;

    private ChallengeData challengeData;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    private List<TppMessage> tppMessages;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Amount getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Amount transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Boolean getTransactionFeeIndicator() {
        return transactionFeeIndicator;
    }

    public void setTransactionFeeIndicator(Boolean transactionFeeIndicator) {
        this.transactionFeeIndicator = transactionFeeIndicator;
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

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public List<TppMessage> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage> tppMessages) {
        this.tppMessages = tppMessages;
    }
}
