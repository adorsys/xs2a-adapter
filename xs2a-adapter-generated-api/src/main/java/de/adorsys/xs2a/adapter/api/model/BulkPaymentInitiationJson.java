package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class BulkPaymentInitiationJson {
    private Boolean batchBookingPreferred;

    private AccountReference debtorAccount;

    private LocalDate requestedExecutionDate;

    private OffsetDateTime requestedExecutionTime;

    private List<PaymentInitiationBulkElementJson> payments;

    private String debtorName;

    public Boolean getBatchBookingPreferred() {
        return batchBookingPreferred;
    }

    public void setBatchBookingPreferred(Boolean batchBookingPreferred) {
        this.batchBookingPreferred = batchBookingPreferred;
    }

    public AccountReference getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReference debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public LocalDate getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public OffsetDateTime getRequestedExecutionTime() {
        return requestedExecutionTime;
    }

    public void setRequestedExecutionTime(OffsetDateTime requestedExecutionTime) {
        this.requestedExecutionTime = requestedExecutionTime;
    }

    public List<PaymentInitiationBulkElementJson> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElementJson> payments) {
        this.payments = payments;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulkPaymentInitiationJson that = (BulkPaymentInitiationJson) o;
        return Objects.equals(batchBookingPreferred, that.batchBookingPreferred) &&
            Objects.equals(debtorAccount, that.debtorAccount) &&
            Objects.equals(requestedExecutionDate, that.requestedExecutionDate) &&
            Objects.equals(requestedExecutionTime, that.requestedExecutionTime) &&
            Objects.equals(payments, that.payments) &&
            Objects.equals(debtorName, that.debtorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchBookingPreferred,
            debtorAccount,
            requestedExecutionDate,
            requestedExecutionTime,
            payments,
            debtorName);
    }
}
