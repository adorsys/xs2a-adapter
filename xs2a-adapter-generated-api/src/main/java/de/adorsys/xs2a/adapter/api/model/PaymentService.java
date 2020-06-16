package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum PaymentService {
    PAYMENTS("payments"),

    BULK_PAYMENTS("bulk-payments"),

    PERIODIC_PAYMENTS("periodic-payments");

    private String value;

    PaymentService(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PaymentService fromValue(String value) {
        for (PaymentService e : PaymentService.values()) {
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
