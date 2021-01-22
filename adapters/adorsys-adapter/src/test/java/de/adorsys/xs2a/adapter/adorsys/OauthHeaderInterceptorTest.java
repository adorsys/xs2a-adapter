package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.adorsys.xs2a.adapter.adorsys.OauthHeaderInterceptor.OAUTH_HEADER_NAME;

class OauthHeaderInterceptorTest {

    private String header;
    private OauthHeaderInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        header = AdapterConfig.readProperty(OAUTH_HEADER_NAME);
        interceptor = new OauthHeaderInterceptor();
    }

    @Test
    void checkHeaderValueIsPreStep() {
        Request.Builder result = applyInterceptor("90001001");

        Assertions.assertEquals("pre-step", result.headers().get(header));
    }

    @Test
    void checkHeaderValueIsIntegrated() {
        Request.Builder result = applyInterceptor("90001002");

        Assertions.assertEquals("integrated", result.headers().get(header));
    }

    @Test
    void willNotOverrideHeader() {
        Request.Builder builder = initRequestBuilder("90001002");
        builder.header("X-oauth-PREFERRED", "pre-step");
        Request.Builder result = interceptor.preHandle(builder);

        // the value stays "pre-step" instead of being set to "integrated" by the interceptor
        Assertions.assertEquals("pre-step", result.headers().get(header));
    }

    private Request.Builder applyInterceptor(String bankCode) {
        Request.Builder builder = initRequestBuilder(bankCode);
        interceptor.preHandle(builder);
        return builder;
    }

    private Request.Builder initRequestBuilder(String bankCode) {
        RequestBuilderImpl builder = new RequestBuilderImpl(null, null, null);
        builder.header(RequestHeaders.X_GTW_BANK_CODE, bankCode);
        return builder;
    }

    @Test
    void applyRequestBankCodeIsAbsent() {

        RequestBuilderImpl builder = new RequestBuilderImpl(null, null, null);

        interceptor.preHandle(builder);

        Assertions.assertEquals(0, builder.headers().size());
    }
}
