package de.adorsys.xs2a.adapter.service.psd2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
        throw new IllegalArgumentException(value);
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
