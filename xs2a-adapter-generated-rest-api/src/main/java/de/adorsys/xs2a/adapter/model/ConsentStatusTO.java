package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ConsentStatusTO {
    RECEIVED("received"),

    REJECTED("rejected"),

    VALID("valid"),

    REVOKEDBYPSU("revokedByPsu"),

    EXPIRED("expired"),

    TERMINATEDBYTPP("terminatedByTpp"),

    PARTIALLYAUTHORISED("partiallyAuthorised");

    private String value;

    ConsentStatusTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ConsentStatusTO fromValue(String value) {
        for (ConsentStatusTO e : ConsentStatusTO.values()) {
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
