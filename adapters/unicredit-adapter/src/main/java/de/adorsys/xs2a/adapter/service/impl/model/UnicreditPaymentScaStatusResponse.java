package de.adorsys.xs2a.adapter.service.impl.model;

import de.adorsys.xs2a.adapter.service.model.TransactionStatus;

public class UnicreditPaymentScaStatusResponse {
    private TransactionStatus transactionStatus;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
