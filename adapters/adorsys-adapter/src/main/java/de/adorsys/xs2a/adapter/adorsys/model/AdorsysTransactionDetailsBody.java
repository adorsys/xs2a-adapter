package de.adorsys.xs2a.adapter.adorsys.model;

import java.util.Objects;

public class AdorsysTransactionDetailsBody {
    private AdorsysTransactions transactionDetails;

    public AdorsysTransactions getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(AdorsysTransactions transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdorsysTransactionDetailsBody that = (AdorsysTransactionDetailsBody) o;
        return Objects.equals(transactionDetails, that.transactionDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionDetails);
    }
}
