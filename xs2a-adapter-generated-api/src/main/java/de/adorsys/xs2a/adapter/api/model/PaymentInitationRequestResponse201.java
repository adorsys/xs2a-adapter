package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PaymentInitationRequestResponse201 {
    private TransactionStatus transactionStatus;

    private String paymentId;

    private Amount transactionFees;

    private Amount currencyConversionFee;

    private Amount estimatedTotalAmount;

    private Amount estimatedInterbankSettlementAmount;

    private Boolean transactionFeeIndicator;

    private List<AuthenticationObject> scaMethods;

    private Object chosenScaMethod;

    private ChallengeData challengeData;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    private List<TppMessage201PaymentInitiation> tppMessages;

    private ScaStatus scaStatus;

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

    public Amount getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Amount transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Amount getCurrencyConversionFee() {
        return currencyConversionFee;
    }

    public void setCurrencyConversionFee(Amount currencyConversionFee) {
        this.currencyConversionFee = currencyConversionFee;
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

    public List<TppMessage201PaymentInitiation> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage201PaymentInitiation> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentInitationRequestResponse201 that = (PaymentInitationRequestResponse201) o;
        return Objects.equals(transactionStatus, that.transactionStatus) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(transactionFees, that.transactionFees) &&
            Objects.equals(currencyConversionFee, that.currencyConversionFee) &&
            Objects.equals(estimatedTotalAmount, that.estimatedTotalAmount) &&
            Objects.equals(estimatedInterbankSettlementAmount, that.estimatedInterbankSettlementAmount) &&
            Objects.equals(transactionFeeIndicator, that.transactionFeeIndicator) &&
            Objects.equals(scaMethods, that.scaMethods) &&
            Objects.equals(chosenScaMethod, that.chosenScaMethod) &&
            Objects.equals(challengeData, that.challengeData) &&
            Objects.equals(links, that.links) &&
            Objects.equals(psuMessage, that.psuMessage) &&
            Objects.equals(tppMessages, that.tppMessages) &&
            Objects.equals(scaStatus, that.scaStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionStatus,
            paymentId,
            transactionFees,
            currencyConversionFee,
            estimatedTotalAmount,
            estimatedInterbankSettlementAmount,
            transactionFeeIndicator,
            scaMethods,
            chosenScaMethod,
            challengeData,
            links,
            psuMessage,
            tppMessages,
            scaStatus);
    }
}
