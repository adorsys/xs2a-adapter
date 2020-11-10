package de.adorsys.xs2a.adapter.comdirect.model;

import java.util.Objects;

public class ComdirectOK200TransactionDetails {
    private ComdirectTransactionDetails transactionsDetails;

    public ComdirectTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(ComdirectTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComdirectOK200TransactionDetails that = (ComdirectOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
