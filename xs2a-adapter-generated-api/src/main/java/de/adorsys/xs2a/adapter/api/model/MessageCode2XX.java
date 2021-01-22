package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum MessageCode2XX {
    WARNING("WARNING");

    private String value;

    MessageCode2XX(String value) {
        this.value = value;
    }

    @JsonCreator
    public static MessageCode2XX fromValue(String value) {
        for (MessageCode2XX e : MessageCode2XX.values()) {
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
