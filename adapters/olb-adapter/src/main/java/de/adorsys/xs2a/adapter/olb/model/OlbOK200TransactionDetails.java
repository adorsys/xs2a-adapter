package de.adorsys.xs2a.adapter.olb.model;

import java.util.Objects;

public class OlbOK200TransactionDetails {
    private OlbTransactionDetails transactionsDetails;

    public OlbTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(OlbTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OlbOK200TransactionDetails that = (OlbOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
