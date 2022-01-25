/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.ing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;

public enum IngPaymentProduct {
    SEPA_CREDIT_TRANSFERS("sepa-credit-transfers"),

    CROSS_BORDER_CREDIT_TRANSFERS("cross-border-credit-transfers"),

    DOMESTIC_CREDIT_TRANSFERS("domestic-credit-transfers");

    private final String value;

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

    public static IngPaymentProduct of(PaymentProduct paymentProduct) {
        return fromValue(paymentProduct.toString());
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
