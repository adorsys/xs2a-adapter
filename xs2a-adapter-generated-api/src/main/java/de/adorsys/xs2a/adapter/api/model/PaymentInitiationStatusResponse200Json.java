package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class PaymentInitiationStatusResponse200Json {
    private TransactionStatus transactionStatus;

    private Boolean fundsAvailable;

    private String psuMessage;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Boolean getFundsAvailable() {
        return fundsAvailable;
    }

    public void setFundsAvailable(Boolean fundsAvailable) {
        this.fundsAvailable = fundsAvailable;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentInitiationStatusResponse200Json that = (PaymentInitiationStatusResponse200Json) o;
        return Objects.equals(transactionStatus, that.transactionStatus) &&
            Objects.equals(fundsAvailable, that.fundsAvailable) &&
            Objects.equals(psuMessage, that.psuMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionStatus,
            fundsAvailable,
            psuMessage);
    }
}
