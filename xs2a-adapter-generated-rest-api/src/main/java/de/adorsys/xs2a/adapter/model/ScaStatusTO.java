package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ScaStatusTO {
    RECEIVED("received"),

    PSUIDENTIFIED("psuIdentified"),

    PSUAUTHENTICATED("psuAuthenticated"),

    SCAMETHODSELECTED("scaMethodSelected"),

    STARTED("started"),

    UNCONFIRMED("unconfirmed"),

    FINALISED("finalised"),

    FAILED("failed"),

    EXEMPTED("exempted");

    private String value;

    ScaStatusTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ScaStatusTO fromValue(String value) {
        for (ScaStatusTO e : ScaStatusTO.values()) {
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
