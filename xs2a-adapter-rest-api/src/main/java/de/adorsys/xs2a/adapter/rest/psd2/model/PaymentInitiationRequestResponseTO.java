package de.adorsys.xs2a.adapter.rest.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class PaymentInitiationRequestResponseTO {
    private String transactionStatus;

    private String paymentId;

    private AmountTO transactionFees;

    private Boolean transactionFeeIndicator;

    private List<AuthenticationObjectTO> scaMethods;

    private AuthenticationObjectTO chosenScaMethod;

    private ChallengeDataTO challengeData;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    private String psuMessage;

    private List<TppMessageTO> tppMessages;

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

    public AmountTO getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(AmountTO transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Boolean getTransactionFeeIndicator() {
        return transactionFeeIndicator;
    }

    public void setTransactionFeeIndicator(Boolean transactionFeeIndicator) {
        this.transactionFeeIndicator = transactionFeeIndicator;
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

    public List<TppMessageTO> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessageTO> tppMessages) {
        this.tppMessages = tppMessages;
    }
}
