package de.adorsys.xs2a.adapter.service.model;

import de.adorsys.xs2a.adapter.service.model.TransactionStatus;

public class PaymentInitiationStatus {
    private TransactionStatus transactionStatus;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
