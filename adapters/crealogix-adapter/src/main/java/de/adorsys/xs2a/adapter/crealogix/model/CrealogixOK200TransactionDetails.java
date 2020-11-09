package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.Objects;

public class CrealogixOK200TransactionDetails {
    private CrealogixTransactionDetails transactionsDetails;

    public CrealogixTransactionDetails getTransactionsDetails() {
        return transactionsDetails;
    }

    public void setTransactionsDetails(CrealogixTransactionDetails transactionsDetails) {
        this.transactionsDetails = transactionsDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrealogixOK200TransactionDetails that = (CrealogixOK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
