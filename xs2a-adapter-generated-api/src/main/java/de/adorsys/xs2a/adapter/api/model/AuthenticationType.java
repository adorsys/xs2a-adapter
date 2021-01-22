package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum AuthenticationType {
    SMS_OTP("SMS_OTP"),

    CHIP_OTP("CHIP_OTP"),

    PHOTO_OTP("PHOTO_OTP"),

    PUSH_OTP("PUSH_OTP"),

    SMTP_OTP("SMTP_OTP"),

    EMAIL("EMAIL");

    private String value;

    AuthenticationType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static AuthenticationType fromValue(String value) {
        for (AuthenticationType e : AuthenticationType.values()) {
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
