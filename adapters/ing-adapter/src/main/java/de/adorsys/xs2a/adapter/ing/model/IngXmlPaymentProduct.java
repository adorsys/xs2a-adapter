package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;

public enum IngXmlPaymentProduct {
    PAIN_001_SEPA_CREDIT_TRANSFERS("pain.001-sepa-credit-transfers"),

    PAIN_001_CROSS_BORDER_CREDIT_TRANSFERS("pain.001-cross-border-credit-transfers"),

    PAIN_001_DOMESTIC_CREDIT_TRANSFERS("pain.001-domestic-credit-transfers");

    private final String value;

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

    public static IngXmlPaymentProduct of(PaymentProduct paymentProduct) {
        return fromValue(paymentProduct.toString());
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
