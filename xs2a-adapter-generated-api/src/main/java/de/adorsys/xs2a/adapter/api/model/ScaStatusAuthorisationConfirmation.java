package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ScaStatusAuthorisationConfirmation {
    FINALISED("finalised"),

    FAILED("failed");

    private String value;

    ScaStatusAuthorisationConfirmation(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ScaStatusAuthorisationConfirmation fromValue(String value) {
        for (ScaStatusAuthorisationConfirmation e : ScaStatusAuthorisationConfirmation.values()) {
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
