package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IngInstructionPriority {

    HIGH("HIGH"),

    NORM("NORM");

    private final String value;

    IngInstructionPriority(String value) {
        this.value = value;
    }

    @JsonCreator
    public static IngInstructionPriority of(String value) {
        for (IngInstructionPriority e : IngInstructionPriority.values()) {
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

