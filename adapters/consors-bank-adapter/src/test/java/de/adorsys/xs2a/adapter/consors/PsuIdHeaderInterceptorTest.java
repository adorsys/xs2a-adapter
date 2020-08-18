package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
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
        Request.Builder actual = interceptor.apply(builder);
        assertEquals(PSU_ID, actual.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void apply_psuIdQuotes() {
        builder.header(RequestHeaders.PSU_ID, QUOTES);
        Request.Builder actual = interceptor.apply(builder);
        assertNull(actual.headers().get(RequestHeaders.PSU_ID));
    }

    @Test
    void apply_noPsuIdHeader() {
        builder.header(RANDOM_HEADER, RANDOM_HEADER);
        Request.Builder actual = interceptor.apply(builder);
        assertFalse(actual.headers().containsKey(RequestHeaders.PSU_ID));
        assertEquals(actual.headers(), builder.headers());
    }
}
