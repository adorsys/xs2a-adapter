package de.adorsys.xs2a.adapter.service.psd2.model;

import java.time.LocalDate;
import java.util.List;

public class BulkPaymentInitiationWithStatusResponse {
    private Boolean batchBookingPreferred;

    private LocalDate requestedExecutionDate;

    private AccountReference debtorAccount;

    private List<PaymentInitiationBulkElement> payments;

    private String transactionStatus;

    public Boolean getBatchBookingPreferred() {
        return batchBookingPreferred;
    }

    public void setBatchBookingPreferred(Boolean batchBookingPreferred) {
        this.batchBookingPreferred = batchBookingPreferred;
    }

    public LocalDate getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public AccountReference getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReference debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public List<PaymentInitiationBulkElement> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElement> payments) {
        this.payments = payments;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
