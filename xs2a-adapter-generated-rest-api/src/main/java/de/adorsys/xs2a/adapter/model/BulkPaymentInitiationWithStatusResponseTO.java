package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class BulkPaymentInitiationWithStatusResponseTO {
    private Boolean batchBookingPreferred;

    private LocalDate requestedExecutionDate;

    private AccountReferenceTO debtorAccount;

    private List<PaymentInitiationBulkElementJsonTO> payments;

    private TransactionStatusTO transactionStatus;

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

    public List<PaymentInitiationBulkElementJsonTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInitiationBulkElementJsonTO> payments) {
        this.payments = payments;
    }

    public TransactionStatusTO getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusTO transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
