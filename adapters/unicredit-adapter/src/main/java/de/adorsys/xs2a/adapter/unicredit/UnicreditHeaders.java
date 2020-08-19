/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
