package de.adorsys.xs2a.adapter.unicredit.model;

import java.util.Objects;

public class UnicreditOK200TransactionDetails {
    private UnicreditTransactionDetails transactionsDetails;

    public UnicreditTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(UnicreditTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnicreditOK200TransactionDetails that = (UnicreditOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
