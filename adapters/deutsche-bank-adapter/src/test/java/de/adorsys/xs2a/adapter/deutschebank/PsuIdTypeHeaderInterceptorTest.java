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

class PsuIdTypeHeaderInterceptorTest {

    private PsuIdTypeHeaderInterceptor interceptor;
    private RequestBuilderImpl builder;

    @BeforeEach
    public void setUp() {
        interceptor = new PsuIdTypeHeaderInterceptor();
        builder = new RequestBuilderImpl(null, null, null);
        builder.header(RequestHeaders.PSU_ID, "CHARLES_DICKENS");
    }

    @Test
    void setsPsuIdTypeForDeutscheBankInGermanyWhenPsuIdIsPresent() {
        builder.uri("https://xs2a.db.com/ais/DE/DB");
        interceptor.preHandle(builder);
        assertEquals("DE_ONLB_DB", builder.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    void doesNothingWhenPsuIdIsNotSet() {
        builder.uri("https://xs2a.db.com/ais/DE/DB");
        builder.headers().remove(RequestHeaders.PSU_ID);
        interceptor.preHandle(builder);
        assertNull(builder.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    void doesNothingIfPsuIdTypeIsAlreadySet() {
        builder.uri("https://xs2a.db.com/ais/DE/DB");
        String psuIdTypeOverride = "PSU_ID_TYPE_OVERRIDE";
        builder.header(RequestHeaders.PSU_ID_TYPE, psuIdTypeOverride);
        interceptor.preHandle(builder);
        assertEquals(psuIdTypeOverride, builder.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    void setsPsuIdTypeForPostbankInGermany() {
        builder.uri("https://xs2a.db.com/ais/DE/Postbank");
        interceptor.preHandle(builder);
        assertEquals("DE_ONLB_POBA", builder.headers().get(RequestHeaders.PSU_ID_TYPE));
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

    @Test
    void setsPsuIdTypeForNorisbankInGermany() {
        builder.uri("https://xs2a.db.com/ais/DE/Noris");
        interceptor.preHandle(builder);
        assertEquals("DE_ONLB_NORIS", builder.headers().get(RequestHeaders.PSU_ID_TYPE));
    }
}
