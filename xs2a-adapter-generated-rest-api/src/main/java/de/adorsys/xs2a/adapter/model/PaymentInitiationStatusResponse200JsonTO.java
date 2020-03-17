package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationStatusResponse200JsonTO {
    private TransactionStatusTO transactionStatus;

    private Boolean fundsAvailable;

    private String psuMessage;

    public TransactionStatusTO getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusTO transactionStatus) {
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
