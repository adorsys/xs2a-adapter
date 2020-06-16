package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum BalanceType {
    CLOSINGBOOKED("closingBooked"),

    EXPECTED("expected"),

    OPENINGBOOKED("openingBooked"),

    INTERIMAVAILABLE("interimAvailable"),

    INTERIMBOOKED("interimBooked"),

    FORWARDAVAILABLE("forwardAvailable"),

    NONINVOICED("nonInvoiced");

    private String value;

    BalanceType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static BalanceType fromValue(String value) {
        for (BalanceType e : BalanceType.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
