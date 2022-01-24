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

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class RequestHeadersTest {

    @Test
    void getIsCaseAgnostic() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(singletonMap("X-gtw-ASPSP-id", "1"));
        Optional<String> aspspId = requestHeaders.get(RequestHeaders.X_GTW_ASPSP_ID);
        assertThat(aspspId).get().isEqualTo("1");
    }

    @Test
    void getUnknownHeaderReturnsEmpty() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(emptyMap());
        Optional<String> headerValue = requestHeaders.get("asdfasdf");
        assertThat(headerValue).isEmpty();
    }

    @Test
    void permitsOauthPreferredHeaders() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(singletonMap("X-OAUTH-PREFERRED", "pre-step"));
        assertThat(requestHeaders.get("X-OAUTH-PREFERRED")).get().isEqualTo("pre-step");
    }

    @Test
    void notPermitsCustomHeaders() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(singletonMap("x-custom-header", "value"));
        assertThat(requestHeaders.toMap())
            .isEmpty();
    }
}
