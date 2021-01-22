package de.adorsys.xs2a.adapter.adorsys.model;

import java.util.Objects;

public class AdorsysOK200TransactionDetails {
    private AdorsysTransactionDetailsBody transactionsDetails;

    public AdorsysTransactionDetailsBody getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(AdorsysTransactionDetailsBody transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdorsysOK200TransactionDetails that = (AdorsysOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
