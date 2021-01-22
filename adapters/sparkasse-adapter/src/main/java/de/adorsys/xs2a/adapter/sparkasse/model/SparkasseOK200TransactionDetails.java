package de.adorsys.xs2a.adapter.sparkasse.model;

import java.util.Objects;

public class SparkasseOK200TransactionDetails {
    private SparkasseTransactionDetails transactionsDetails;

    public SparkasseTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(SparkasseTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SparkasseOK200TransactionDetails that = (SparkasseOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
