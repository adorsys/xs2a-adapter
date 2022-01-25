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

package de.adorsys.xs2a.adapter.api;

import java.util.*;

public class ResponseHeaders {
    public static final String LOCATION = "Location";
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String ASPSP_SCA_APPROACH = "ASPSP-SCA-Approach";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String X_GTW_ASPSP_CHANGES_DETECTED = "X-GTW-ASPSP-CHANGES-DETECTED";

    private static Map<String, String> headerNamesLowerCased = new HashMap<>();
    static {
        headerNamesLowerCased.put(LOCATION.toLowerCase(), LOCATION);
        headerNamesLowerCased.put(X_REQUEST_ID.toLowerCase(), X_REQUEST_ID);
        headerNamesLowerCased.put(ASPSP_SCA_APPROACH.toLowerCase(), ASPSP_SCA_APPROACH);
        headerNamesLowerCased.put(CONTENT_TYPE.toLowerCase(), CONTENT_TYPE);
        headerNamesLowerCased.put(X_GTW_ASPSP_CHANGES_DETECTED.toLowerCase(), X_GTW_ASPSP_CHANGES_DETECTED);
    }

    private Map<String, String> headers;

    private ResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static ResponseHeaders fromMap(Map<String, String> headersMap) {
        if (Objects.isNull(headersMap) || headersMap.isEmpty()) {
            return emptyResponseHeaders();
        }

        Map<String, String> headers = new HashMap<>();

        Set<String> headerNamesLowerCased = ResponseHeaders.headerNamesLowerCased.keySet();

        headersMap.forEach((name, value) -> {
            String headerNameInLowerCase = name.toLowerCase();
            if (isHeaderExistInBGSpecification(headerNamesLowerCased, headerNameInLowerCase)) {
                headers.put(ResponseHeaders.headerNamesLowerCased.get(headerNameInLowerCase), value);
            }
        });
        return new ResponseHeaders(headers);
    }

    public static ResponseHeaders emptyResponseHeaders() {
        return new ResponseHeaders(Collections.emptyMap());
    }

    private static boolean isHeaderExistInBGSpecification(Set<String> headerNames, String headerName) {
        return headerNames.contains(headerName);
    }

    public Map<String, String> getHeadersMap() {
        return new HashMap<>(headers);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
}
