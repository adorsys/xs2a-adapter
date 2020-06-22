package de.adorsys.xs2a.adapter.service.ing.internal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentProduct {
    SEPA_CREDIT_TRANSFERS("sepa-credit-transfers"),

    CROSS_BORDER_CREDIT_TRANSFERS("cross-border-credit-transfers"),

    DOMESTIC_CREDIT_TRANSFERS("domestic-credit-transfers");

    private String value;

    PaymentProduct(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PaymentProduct fromValue(String value) {
        for (PaymentProduct e : PaymentProduct.values()) {
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
