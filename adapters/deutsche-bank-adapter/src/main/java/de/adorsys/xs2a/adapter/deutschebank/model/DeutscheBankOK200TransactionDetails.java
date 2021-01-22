package de.adorsys.xs2a.adapter.deutschebank.model;

import java.util.Objects;

public class DeutscheBankOK200TransactionDetails {
    private DeutscheBankTransactionDetails transactionsDetails;

    public DeutscheBankTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(DeutscheBankTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeutscheBankOK200TransactionDetails that = (DeutscheBankOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
