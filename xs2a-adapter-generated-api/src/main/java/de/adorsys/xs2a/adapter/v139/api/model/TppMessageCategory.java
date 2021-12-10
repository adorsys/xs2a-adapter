package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum TppMessageCategory {
    ERROR("ERROR"),

    WARNING("WARNING");

    private String value;

    TppMessageCategory(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TppMessageCategory fromValue(String value) {
        for (TppMessageCategory e : TppMessageCategory.values()) {
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
