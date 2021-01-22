package de.adorsys.xs2a.adapter.fiducia.model;

import java.util.Objects;

public class FiduciaOK200TransactionDetails {
    private FiduciaTransactionDetails transactionsDetails;

    public FiduciaTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(FiduciaTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaOK200TransactionDetails that = (FiduciaOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
