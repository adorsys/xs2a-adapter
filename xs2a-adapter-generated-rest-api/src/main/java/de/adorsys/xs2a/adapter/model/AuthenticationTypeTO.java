package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum AuthenticationTypeTO {
    SMS_OTP("SMS_OTP"),

    CHIP_OTP("CHIP_OTP"),

    PHOTO_OTP("PHOTO_OTP"),

    PUSH_OTP("PUSH_OTP");

    private String value;

    AuthenticationTypeTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AuthenticationTypeTO fromValue(String value) {
        for (AuthenticationTypeTO e : AuthenticationTypeTO.values()) {
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
