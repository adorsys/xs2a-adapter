package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaymentInitiationResponse {
    private String transactionStatus;

    private String paymentId;

    @JsonProperty("_links")
    private Links links;

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

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<TppMessage> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage> tppMessages) {
        this.tppMessages = tppMessages;
    }
}
