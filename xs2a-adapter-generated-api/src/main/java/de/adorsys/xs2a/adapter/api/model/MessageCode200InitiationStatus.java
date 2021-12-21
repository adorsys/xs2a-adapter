package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum MessageCode200InitiationStatus {
    FUNDS_NOT_AVAILABLE("FUNDS_NOT_AVAILABLE");

    private String value;

    MessageCode200InitiationStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static MessageCode200InitiationStatus fromValue(String value) {
        for (MessageCode200InitiationStatus e : MessageCode200InitiationStatus.values()) {
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
