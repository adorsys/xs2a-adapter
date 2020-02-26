package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PsuIdTypeHeaderInterceptorTest {

    private PsuIdTypeHeaderInterceptor interceptor;
    private RequestBuilderImpl builder;

    @BeforeEach
    public void setUp() {
        interceptor = new PsuIdTypeHeaderInterceptor();
        builder = new RequestBuilderImpl(null, null, null);
        builder.header(RequestHeaders.PSU_ID, "CHARLES_DICKENS");
    }

    @Test
    public void setsPsuIdTypeForDeutscheBankInGermanyWhenPsuIdIsPresent() {
        builder.uri("https://xs2a.db.com/ais/DE/PFB");
        Request.Builder actual = interceptor.apply(builder);
        assertEquals("DE_ONLB_DB", actual.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    public void doesNothingWhenPsuIdIsNotSet() {
        builder.uri("https://xs2a.db.com/ais/DE/PFB");
        builder.headers().remove(RequestHeaders.PSU_ID);
        Request.Builder actual = interceptor.apply(builder);
        assertNull(actual.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    public void doesNothingIfPsuIdTypeIsAlreadySet() {
        builder.uri("https://xs2a.db.com/ais/DE/PFB");
        String psuIdTypeOverride = "PSU_ID_TYPE_OVERRIDE";
        builder.header(RequestHeaders.PSU_ID_TYPE, psuIdTypeOverride);
        Request.Builder actual = interceptor.apply(builder);
        assertEquals(psuIdTypeOverride, actual.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    public void setsPsuIdTypeForPostbankInGermany() {
        builder.uri("https://xs2a.db.com/ais/DE/Postbank");
        Request.Builder actual = interceptor.apply(builder);
        assertEquals("DE_ONLB_POBA", actual.headers().get(RequestHeaders.PSU_ID_TYPE));
    }

    @Test
    public void hasBoundsChecksInCasePathIsTooShort() {
        builder.uri("https://xs2a.db.com/");
        try {
            interceptor.apply(builder);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
    }

    @Test
    public void setsPsuIdTypeForNorisbankInGermany() {
        builder.uri("https://xs2a.db.com/ais/DE/Noris");
        Request.Builder actual = interceptor.apply(builder);
        assertEquals("DE_ONLB_NORIS", actual.headers().get(RequestHeaders.PSU_ID_TYPE));
    }
}
