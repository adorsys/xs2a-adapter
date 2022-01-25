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

package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PsuIdHeaderInterceptorTest {
    private PsuIdHeaderInterceptor interceptor;
    private RequestBuilderImpl builder;

    @BeforeEach
    public void setUp() {
        interceptor = new PsuIdHeaderInterceptor();
        builder = new RequestBuilderImpl(null, null, null);
    }

    @Test
    void trimsPsuIdForDeutscheBankInGermanyWhenPsuIdLengthIsTwelve() {
        builder.uri("https://xs2a.db.com/ais/DE/DB");
        builder.header(RequestHeaders.PSU_ID, "123456789012");
        interceptor.preHandle(builder);
        assertEquals("1234567890", builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void trimsPsuIdForNorisbankInGermanyWhenPsuIdLengthIsTwelve() {
        builder.uri("https://xs2a.db.com/ais/DE/Noris");
        builder.header(RequestHeaders.PSU_ID, "123456789012");
        interceptor.preHandle(builder);
        assertEquals("1234567890", builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void doesNothingForPostbankInGermanyWhenPsuIdLengthIsTwelve() {
        builder.uri("https://xs2a.db.com/ais/DE/Postbank");
        builder.header(RequestHeaders.PSU_ID, "123456789012");
        interceptor.preHandle(builder);
        assertEquals("123456789012", builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void doesNothingForDeutscheBankInGermanyWhenPsuIdLengthIsTen() {
        builder.uri("https://xs2a.db.com/ais/DE/DB");
        builder.header(RequestHeaders.PSU_ID, "1234567890");
        interceptor.preHandle(builder);
        assertEquals("1234567890", builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void doesNothingForNorisbankInGermanyWhenPsuIdLengthIsTen() {
        builder.uri("https://xs2a.db.com/ais/DE/Noris");
        builder.header(RequestHeaders.PSU_ID, "1234567890");
        interceptor.preHandle(builder);
        assertEquals("1234567890", builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void doesNothingForNorisbankInGermanyWhenPsuIdLengthIsEleven() {
        builder.uri("https://xs2a.db.com/ais/DE/Noris");
        builder.header(RequestHeaders.PSU_ID, "12345678901");
        interceptor.preHandle(builder);
        assertEquals("12345678901", builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void doesNothingWhenPsuIdIsNotSet() {
        builder.uri("https://xs2a.db.com/ais/DE/DB");
        interceptor.preHandle(builder);
        assertNull(builder.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void hasBoundsChecksInCasePathIsTooShort() {
        builder.uri("https://xs2a.db.com/");
        try {
            interceptor.preHandle(builder);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
    }
}
