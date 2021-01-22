package de.adorsys.xs2a.adapter.santander.model;

import java.util.Objects;

public class SantanderOK200TransactionDetails {
    private SantanderTransactionDetails transactionsDetails;

    public SantanderTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(SantanderTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SantanderOK200TransactionDetails that = (SantanderOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
