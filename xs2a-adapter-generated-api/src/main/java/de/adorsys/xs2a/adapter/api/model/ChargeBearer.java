package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ChargeBearer {
    DEBT("DEBT"),

    CRED("CRED"),

    SHAR("SHAR"),

    SLEV("SLEV");

    private String value;

    ChargeBearer(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ChargeBearer fromValue(String value) {
        for (ChargeBearer e : ChargeBearer.values()) {
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
