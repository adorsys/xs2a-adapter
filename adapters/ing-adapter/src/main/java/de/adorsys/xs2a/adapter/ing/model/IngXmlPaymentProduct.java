package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IngXmlPaymentProduct {
    PAIN_001_SEPA_CREDIT_TRANSFERS("pain.001-sepa-credit-transfers"),

    PAIN_001_CROSS_BORDER_CREDIT_TRANSFERS("pain.001-cross-border-credit-transfers"),

    PAIN_001_DOMESTIC_CREDIT_TRANSFERS("pain.001-domestic-credit-transfers");

    private String value;

    IngXmlPaymentProduct(String value) {
        this.value = value;
    }

    @JsonCreator
    public static IngXmlPaymentProduct fromValue(String value) {
        for (IngXmlPaymentProduct e : IngXmlPaymentProduct.values()) {
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
