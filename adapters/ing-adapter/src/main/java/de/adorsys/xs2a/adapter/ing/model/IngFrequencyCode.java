package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IngFrequencyCode {

    DAIL("DAIL"),

    WEEK("WEEK"),

    TOWK("TOWK"),

    MNTH("MNTH"),

    TOMN("TOMN"),

    FOMN("FOMN"),

    QUTR("QUTR"),

    SEMI("SEMI"),

    YEAR("YEAR");

    private final String value;

    IngFrequencyCode(String value) {
        this.value = value;
    }

    @JsonCreator
    public static IngFrequencyCode of(String value) {
        for (IngFrequencyCode e : IngFrequencyCode.values()) {
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

