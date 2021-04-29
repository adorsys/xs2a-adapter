package de.adorsys.xs2a.adapter.sparkasse.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SparkasseAuthenticationType {
    SMS_OTP("SMS_OTP"),

    CHIP_OTP("CHIP_OTP"),

    PHOTO_OTP("PHOTO_OTP"),

    PUSH_OTP("PUSH_OTP"),

    PUSH_DEC("PUSH_DEC"),

    SMTP_OTP("SMTP_OTP"),

    EMAIL("EMAIL");

    private final String value;

    SparkasseAuthenticationType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static SparkasseAuthenticationType fromValue(String value) {
        for (SparkasseAuthenticationType e : SparkasseAuthenticationType.values()) {
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
