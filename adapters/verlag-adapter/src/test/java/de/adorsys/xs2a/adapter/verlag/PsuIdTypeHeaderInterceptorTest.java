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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PsuIdTypeHeaderInterceptorTest {

    private static final String METHOD = "GET";
    private static final String URI = "https://uri.com";
    private static final String APPLICATION_JSON = "application/json";
    private static final String PSUID = "psu_id";
    private static final String ASPSP_ID = UUID.randomUUID().toString();

    private static final String EMPTY_VALUE = "  ";
    private static final String SOME_PSU_ID_TYPE = "SOME_PSU_ID_TYPE";

    private final Interceptor interceptor = new PsuIdTypeHeaderInterceptor();
    private final Request.Builder builder = new RequestBuilderImpl(mock(HttpClient.class), METHOD, URI);

    @BeforeEach
    public void setUp() {
        populateHeader(builder);
    }

    @Test
    void apply_noPsuIdType() {
        interceptor.preHandle(builder);

        assertFalse(builder.headers().containsKey(PSU_ID_TYPE));
        checkAssertions(builder);
    }


    @Test
    void apply_psuIdTypeWithoutValue() {
        builder.header(PSU_ID_TYPE, "");

        interceptor.preHandle(builder);

        assertFalse(builder.headers().containsKey(PSU_ID_TYPE));
        checkAssertions(builder);
    }

    @Test
    void apply_psuIdTypeWithEmptyValue() {
        builder.header(PSU_ID_TYPE, EMPTY_VALUE);

        interceptor.preHandle(builder);

        assertFalse(builder.headers().containsKey(PSU_ID_TYPE));
        checkAssertions(builder);
    }

    @Test
    void apply_psuIdTypeWithValue() {
        builder.header(PSU_ID_TYPE, SOME_PSU_ID_TYPE);

        interceptor.preHandle(builder);

        assertTrue(builder.headers().containsKey(PSU_ID_TYPE));
        assertEquals(SOME_PSU_ID_TYPE, builder.headers().get(PSU_ID_TYPE));
        checkAssertions(builder);
    }

    private void populateHeader(Request.Builder builder) {
        builder.headers(buildHeaders());
    }

    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put(X_GTW_ASPSP_ID, ASPSP_ID);
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(PSU_ID, PSUID);

        return headers;
    }

    private void checkAssertions(Request.Builder builder) {
        assertTrue(builder.headers().containsKey(X_GTW_ASPSP_ID));
        assertEquals(ASPSP_ID, builder.headers().get(X_GTW_ASPSP_ID));
        assertTrue(builder.headers().containsKey(CONTENT_TYPE));
        assertEquals(APPLICATION_JSON, builder.headers().get(CONTENT_TYPE));
        assertTrue(builder.headers().containsKey(PSU_ID));
        assertEquals(PSUID, builder.headers().get(PSU_ID));
        assertEquals(URI, builder.uri());
        assertEquals(METHOD, builder.method());
    }
}
