package de.adorsys.xs2a.adapter.commerzbank.model;

import java.util.Objects;

public class CommerzbankOK200TransactionDetails {
    private CommerzbankTransactionDetails transactionsDetails;

    public CommerzbankTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(CommerzbankTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommerzbankOK200TransactionDetails that = (CommerzbankOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
