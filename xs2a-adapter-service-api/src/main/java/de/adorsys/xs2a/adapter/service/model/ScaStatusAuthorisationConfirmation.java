package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
        throw new IllegalArgumentException(value);
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
