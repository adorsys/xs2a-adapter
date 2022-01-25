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

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

enum WiremockSupportedHeader {
    X_REQUEST_ID("X-Request-ID"),
    CONSENT_ID("Consent-ID"),
    DIGEST("Digest"),
    PSU_ID("PSU-ID"),
    PSU_CORPORATE_ID("PSU-Corporate-ID"),
    TPP_REDIRECT_URI("TPP-Redirect-URI"),
    DATE("Date"),
    SIGNATURE("Signature"),
    TPP_SIGNATURE_CERTIFICATE("TPP-Signature-Certificate"),
    PSU_IP_ADDRESS("PSU-IP-Address"),
    PSU_ID_TYPE("PSU-ID-Type"),
    PSU_CORPORATE_ID_TYPE("PSU-Corporate-ID-Type"),
    TPP_REDIRECT_PREFERRED("TPP-Redirect-Preferred"),
    TPP_NOK_REDIRECT_URI("TPP-Nok-Redirect-URI"),
    TPP_EXPLICIT_AUTHORISATION_PREFERRED("TPP-Explicit-Authorisation-Preferred"),
    PSU_IP_PORT("PSU-IP-Port"),
    PSU_ACCEPT("PSU-Accept"),
    PSU_ACCEPT_CHARSET("PSU-Accept-Charset"),
    PSU_ACCEPT_ENCODING("PSU-Accept-Encoding"),
    PSU_ACCEPT_LANGUAGE("PSU-Accept-Language"),
    PSU_USER_AGENT("PSU-User-Agent"),
    PSU_HTTP_METHOD("PSU-Http-Method"),
    PSU_DEVICE_ID("PSU-Device-ID"),
    PSU_GEO_LOCATION("PS-Geo-Location"),
    CONTENT_TYPE("Content-Type") {
        @Override
        boolean isEqual(String stubValue, String currentValue) {
            if (isEmpty(stubValue) || isEmpty(currentValue)) {
                return false;
            }
            return currentValue.matches(stubValue);
        }
    },
    AUTHORIZATION("Authorization");

    private final String name;

    WiremockSupportedHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("S1172")
    boolean isEqual(String stubValue, String currentValue) {
        return true;
    }

    static Set<String> set() {
        return Arrays.stream(values())
                   .map(WiremockSupportedHeader::getName)
                   .collect(Collectors.toSet());
    }

    static Optional<WiremockSupportedHeader> resolve(String header) {
        return Arrays.stream(values())
                   .filter(h -> h.getName().equalsIgnoreCase(header))
                   .findFirst();
    }
}
