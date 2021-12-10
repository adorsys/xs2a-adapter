package de.adorsys.xs2a.adapter.v139.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum ConsentStatus {
    RECEIVED("received"),

    REJECTED("rejected"),

    VALID("valid"),

    REVOKEDBYPSU("revokedByPsu"),

    EXPIRED("expired"),

    TERMINATEDBYTPP("terminatedByTpp"),

    PARTIALLYAUTHORISED("partiallyAuthorised");

    private String value;

    ConsentStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ConsentStatus fromValue(String value) {
        for (ConsentStatus e : ConsentStatus.values()) {
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
