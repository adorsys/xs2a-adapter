package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class IngPaymentAgreementStatusResponse {

    private TransactionStatus transactionStatus;

    public enum TransactionStatus {
        RCVD("RCVD"),

        ACTV("ACTV"),

        EXPI("EXPI"),

        CANC("CANC"),

        RJCT("RJCT");

        private final String value;

        TransactionStatus(String value) {
            this.value = value;
        }

        @JsonCreator
        public static TransactionStatus of(String value) {
            for (TransactionStatus e : TransactionStatus.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(value);
        }

        @JsonValue
        @Override
        public String toString() {
            return value;
        }
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}

