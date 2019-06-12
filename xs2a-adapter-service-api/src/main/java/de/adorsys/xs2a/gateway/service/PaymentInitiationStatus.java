package de.adorsys.xs2a.gateway.service;

public class PaymentInitiationStatus {
    private TransactionStatus transactionStatus;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
