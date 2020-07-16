package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IngChargeBearer {

    DEBT("DEBT"),

    CRED("CRED"),

    SHAR("SHAR"),

    SLEV("SLEV");

    private final String value;

    IngChargeBearer(String value) {
        this.value = value;
    }

    @JsonCreator
    public static IngChargeBearer of(String value) {
        for (IngChargeBearer e : IngChargeBearer.values()) {
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

