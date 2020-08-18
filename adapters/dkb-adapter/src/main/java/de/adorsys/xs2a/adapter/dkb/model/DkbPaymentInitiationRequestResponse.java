package de.adorsys.xs2a.adapter.dkb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.*;

import java.util.List;
import java.util.Map;

public class DkbPaymentInitiationRequestResponse {
    // required
    private TransactionStatus transactionStatus;
    private String paymentId;
    @JsonProperty("_links")
    private Map<String, HrefType> links;
    // optional
    private ScaStatus scaStatus;
    private Amount transactionFees;
    private boolean transactionFeeIndicator;
    private boolean multilevelScaRequired;
    private AuthenticationObject[] scaMethods;
    private DkbChallengeData challengeData;
    private String psuMessage;
    private List<TppMessage2XX> tppMessages;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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

    public Amount getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Amount transactionFees) {
        this.transactionFees = transactionFees;
    }

    public boolean isTransactionFeeIndicator() {
        return transactionFeeIndicator;
    }

    public void setTransactionFeeIndicator(boolean transactionFeeIndicator) {
        this.transactionFeeIndicator = transactionFeeIndicator;
    }

    public boolean isMultilevelScaRequired() {
        return multilevelScaRequired;
    }

    public void setMultilevelScaRequired(boolean multilevelScaRequired) {
        this.multilevelScaRequired = multilevelScaRequired;
    }

    public AuthenticationObject[] getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(AuthenticationObject[] scaMethods) {
        this.scaMethods = scaMethods;
    }

    public DkbChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(DkbChallengeData challengeData) {
        this.challengeData = challengeData;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public List<TppMessage2XX> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage2XX> tppMessages) {
        this.tppMessages = tppMessages;
    }
}
