package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IngPaymentProduct {
    SEPA_CREDIT_TRANSFERS("sepa-credit-transfers"),

    CROSS_BORDER_CREDIT_TRANSFERS("cross-border-credit-transfers"),

    DOMESTIC_CREDIT_TRANSFERS("domestic-credit-transfers");

    private String value;

    IngPaymentProduct(String value) {
        this.value = value;
    }

    @JsonCreator
    public static IngPaymentProduct fromValue(String value) {
        for (IngPaymentProduct e : IngPaymentProduct.values()) {
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
