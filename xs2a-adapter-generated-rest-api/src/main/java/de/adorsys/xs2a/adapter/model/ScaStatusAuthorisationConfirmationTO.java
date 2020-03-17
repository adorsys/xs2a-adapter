package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ScaStatusAuthorisationConfirmationTO {
    FINALISED("finalised"),

    FAILED("failed");

    private String value;

    ScaStatusAuthorisationConfirmationTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ScaStatusAuthorisationConfirmationTO fromValue(String value) {
        for (ScaStatusAuthorisationConfirmationTO e : ScaStatusAuthorisationConfirmationTO.values()) {
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
