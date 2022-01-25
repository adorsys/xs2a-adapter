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

public enum IngChargeBearer {

    DEBT("DEBT"),

    CRED("CRED"),

    SHAR("SHAR"),

    SLEV("SLEV");

    private final String value;

    IngChargeBearer(String value) {
        this.value = value;
    }

    @JsonCreator
    public static IngChargeBearer of(String value) {
        for (IngChargeBearer e : IngChargeBearer.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }
}

