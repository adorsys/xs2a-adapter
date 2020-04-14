package de.adorsys.xs2a.adapter.rest.psd2.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class BulkPaymentInitiationWithStatusResponseTO {
    private Boolean batchBookingPreferred;

    private LocalDate requestedExecutionDate;

    private AccountReferenceTO debtorAccount;

    private List<PaymentInitiationBulkElementTO> payments;

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

    public AccountReferenceTO getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(AccountReferenceTO debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public List<PaymentInitiationBulkElementTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElementTO> payments) {
        this.payments = payments;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
