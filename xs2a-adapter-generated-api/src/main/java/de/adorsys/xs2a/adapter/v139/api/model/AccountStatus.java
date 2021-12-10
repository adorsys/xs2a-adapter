package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum AccountStatus {
    ENABLED("enabled"),

    DELETED("deleted"),

    BLOCKED("blocked");

    private String value;

    AccountStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AccountStatus fromValue(String value) {
        for (AccountStatus e : AccountStatus.values()) {
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
