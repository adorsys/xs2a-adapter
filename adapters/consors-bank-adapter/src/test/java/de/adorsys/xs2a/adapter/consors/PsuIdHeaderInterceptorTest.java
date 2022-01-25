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

package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PsuIdHeaderInterceptorTest {

    private static final String PSU_ID = "psu id";
    private static final String QUOTES = "\"\"";
    private static final String RANDOM_HEADER = "random header";

    private PsuIdHeaderInterceptor interceptor;
    private Request.Builder builder;

    @BeforeEach
    public void setUp() {
        interceptor = new PsuIdHeaderInterceptor();
        builder = new RequestBuilderImpl(null, null, null);
    }

    @Test
    void apply_withValidPsuId() {
        builder.header(RequestHeaders.PSU_ID, PSU_ID);
        interceptor.preHandle(builder);
        assertEquals(PSU_ID, builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void apply_psuIdQuotes() {
        builder.header(RequestHeaders.PSU_ID, QUOTES);
        interceptor.preHandle(builder);
        assertNull(builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void apply_noPsuIdHeader() {
        builder.header(RANDOM_HEADER, RANDOM_HEADER);
        interceptor.preHandle(builder);
        assertFalse(builder.headers().containsKey(RequestHeaders.PSU_ID));
        assertEquals(builder.headers(), builder.headers());
    }
}
