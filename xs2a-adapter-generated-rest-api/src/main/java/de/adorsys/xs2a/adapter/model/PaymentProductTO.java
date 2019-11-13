package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-codegen")
public enum PaymentProductTO {
    SEPA_CREDIT_TRANSFERS("sepa-credit-transfers"),

    INSTANT_SEPA_CREDIT_TRANSFERS("instant-sepa-credit-transfers"),

    TARGET_2_PAYMENTS("target-2-payments"),

    CROSS_BORDER_CREDIT_TRANSFERS("cross-border-credit-transfers"),

    PAIN_001_SEPA_CREDIT_TRANSFERS("pain.001-sepa-credit-transfers"),

    PAIN_001_INSTANT_SEPA_CREDIT_TRANSFERS("pain.001-instant-sepa-credit-transfers"),

    PAIN_001_TARGET_2_PAYMENTS("pain.001-target-2-payments"),

    PAIN_001_CROSS_BORDER_CREDIT_TRANSFERS("pain.001-cross-border-credit-transfers");

    private String value;

    PaymentProductTO(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PaymentProductTO fromValue(String value) {
        for (PaymentProductTO e : PaymentProductTO.values()) {
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
