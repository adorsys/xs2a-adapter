package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationStatusResponseTO {
    private String transactionStatus;

    private Boolean fundsAvailable;

    private String psuMessage;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Boolean getFundsAvailable() {
        return fundsAvailable;
    }

    public void setFundsAvailable(Boolean fundsAvailable) {
        this.fundsAvailable = fundsAvailable;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }
}
