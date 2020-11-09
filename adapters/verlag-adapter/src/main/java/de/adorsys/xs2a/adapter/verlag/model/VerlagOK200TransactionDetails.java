package de.adorsys.xs2a.adapter.verlag.model;

import java.util.Objects;

public class VerlagOK200TransactionDetails {
    private VerlagTransactionDetails transactionsDetails;

    public VerlagTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(VerlagTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerlagOK200TransactionDetails that = (VerlagOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
