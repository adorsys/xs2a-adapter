package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class OK200TransactionDetails {
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
        OK200TransactionDetails that = (OK200TransactionDetails) o;
        return Objects.equals(transactionsDetails, that.transactionsDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionsDetails);
    }
}
