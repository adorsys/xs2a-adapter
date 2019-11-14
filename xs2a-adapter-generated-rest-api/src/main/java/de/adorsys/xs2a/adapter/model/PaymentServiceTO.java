package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public enum PaymentServiceTO {
    PAYMENTS("payments"),

    BULK_PAYMENTS("bulk-payments"),

    PERIODIC_PAYMENTS("periodic-payments");

    private String value;

    PaymentServiceTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PaymentServiceTO fromValue(String value) {
        for (PaymentServiceTO e : PaymentServiceTO.values()) {
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
