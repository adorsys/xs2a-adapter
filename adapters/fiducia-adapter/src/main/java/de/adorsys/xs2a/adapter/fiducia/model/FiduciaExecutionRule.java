package de.adorsys.xs2a.adapter.fiducia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FiduciaExecutionRule {
    FOLLOWING("following"),

    // incorrect spelling
    PRECEDING("preceeding");

    private String value;

    FiduciaExecutionRule(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FiduciaExecutionRule fromValue(String value) {
        for (FiduciaExecutionRule e : FiduciaExecutionRule.values()) {
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
