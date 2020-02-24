package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum TransactionStatusTO {
    ACCC("ACCC"),

    ACCP("ACCP"),

    ACSC("ACSC"),

    ACSP("ACSP"),

    ACTC("ACTC"),

    ACWC("ACWC"),

    ACWP("ACWP"),

    RCVD("RCVD"),

    PDNG("PDNG"),

    RJCT("RJCT"),

    CANC("CANC"),

    ACFC("ACFC"),

    PATC("PATC"),

    PART("PART");

    private String value;

    TransactionStatusTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TransactionStatusTO fromValue(String value) {
        for (TransactionStatusTO e : TransactionStatusTO.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
