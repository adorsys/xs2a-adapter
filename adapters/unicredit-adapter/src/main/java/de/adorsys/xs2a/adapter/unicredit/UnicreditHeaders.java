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

package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;

import java.util.*;

public class UnicreditHeaders {

    public static final String HVB_ONLINE_BANKING = "HVB_ONLINEBANKING";
    public static final String UCE_BANKING_GLOBAL = "UCEBANKINGGLOBAL";
    public static final String DEFAULT_PSU_ID_TYPE = HVB_ONLINE_BANKING;
    protected static final Set<String> POSSIBLE_PSU_ID_TYPE_VALUES
        = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(HVB_ONLINE_BANKING, UCE_BANKING_GLOBAL)));

    private UnicreditHeaders() {
    }

    public static Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        if (!POSSIBLE_PSU_ID_TYPE_VALUES.contains(headers.get(RequestHeaders.PSU_ID_TYPE))) {
            headers.put(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
        }

        return headers;
    }
}
