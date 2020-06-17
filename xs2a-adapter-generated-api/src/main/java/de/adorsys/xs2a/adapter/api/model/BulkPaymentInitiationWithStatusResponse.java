package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class BulkPaymentInitiationWithStatusResponse {
    private Boolean batchBookingPreferred;

    private LocalDate requestedExecutionDate;

    private AccountReference debtorAccount;

    private List<PaymentInitiationBulkElementJson> payments;

    private TransactionStatus transactionStatus;

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

    public List<PaymentInitiationBulkElementJson> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElementJson> payments) {
        this.payments = payments;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkPaymentInitiationWithStatusResponse that = (BulkPaymentInitiationWithStatusResponse) o;
        return Objects.equals(batchBookingPreferred, that.batchBookingPreferred) &&
            Objects.equals(requestedExecutionDate, that.requestedExecutionDate) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(payments, that.payments) &&
            Objects.equals(transactionStatus, that.transactionStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchBookingPreferred,
            requestedExecutionDate,
            debtorAccount,
            payments,
            transactionStatus);
    }
}
