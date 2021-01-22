package de.adorsys.xs2a.adapter.sparda.model;

import java.util.Objects;

public class SpardaOK200TransactionDetails {
    private SpardaTransactionDetails transactionsDetails;

    public SpardaTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(SpardaTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpardaOK200TransactionDetails that = (SpardaOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
