package de.adorsys.xs2a.adapter.consors.model;

import de.adorsys.xs2a.adapter.api.model.Transactions;

import java.util.Objects;

public class ConsorsOK200TransactionDetails {
    private Transactions transactionsDetails;

    public Transactions getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(Transactions transactionsDetails) {
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
