package de.adorsys.xs2a.adapter.ing.internal.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum XmlPaymentProduct {
    PAIN_001_SEPA_CREDIT_TRANSFERS("pain.001-sepa-credit-transfers"),

    PAIN_001_CROSS_BORDER_CREDIT_TRANSFERS("pain.001-cross-border-credit-transfers"),

    PAIN_001_DOMESTIC_CREDIT_TRANSFERS("pain.001-domestic-credit-transfers");

    private String value;

    XmlPaymentProduct(String value) {
        this.value = value;
    }

    @JsonCreator
    public static XmlPaymentProduct fromValue(String value) {
        for (XmlPaymentProduct e : XmlPaymentProduct.values()) {
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
