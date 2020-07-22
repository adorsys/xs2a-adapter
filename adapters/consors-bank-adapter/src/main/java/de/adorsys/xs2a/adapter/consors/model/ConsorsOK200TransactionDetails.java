package de.adorsys.xs2a.adapter.consors.model;

import java.util.Objects;

public class ConsorsOK200TransactionDetails {
    private ConsorsTransactionDetails transactionsDetails;

    public ConsorsTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(ConsorsTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsorsOK200TransactionDetails that = (ConsorsOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
