package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExecutionRule {
    FOLLOWING("following"),

    PRECEDING("preceding");

    private String value;

    ExecutionRule(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ExecutionRule fromValue(String value) {
        for (ExecutionRule e : ExecutionRule.values()) {
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
