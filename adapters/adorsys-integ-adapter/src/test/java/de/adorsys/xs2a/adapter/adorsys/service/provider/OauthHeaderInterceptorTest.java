package de.adorsys.xs2a.adapter.adorsys.service.provider;

import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.adorsys.xs2a.adapter.adorsys.service.provider.OauthHeaderInterceptor.OAUTH_HEADER_NAME;

public class OauthHeaderInterceptorTest {

    private String header;
    private OauthHeaderInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        header = AdapterConfig.readProperty(OAUTH_HEADER_NAME);
        interceptor = new OauthHeaderInterceptor();
    }

    @Test
    public void checkHeaderValueIsPreStep() {
        Request.Builder result = applyInterceptor("90001001");

        Assertions.assertEquals(result.headers().get(header), "pre-step");
    }

    @Test
    public void checkHeaderValueIsIntegrated() {
        Request.Builder result = applyInterceptor("90001002");

        Assertions.assertEquals(result.headers().get(header), "integrated");
    }

    private Request.Builder applyInterceptor(String bankCode) {
        Request.Builder builder = initRequestBuilder(bankCode);
        return interceptor.apply(builder);
    }

    private Request.Builder initRequestBuilder(String bankCode) {
        RequestBuilderImpl builder = new RequestBuilderImpl(null, null, null);
        builder.header(RequestHeaders.X_GTW_BANK_CODE, bankCode);
        return builder;
    }

    @Test
    public void applyRequestBankCodeIsAbsent() {

        RequestBuilderImpl builder = new RequestBuilderImpl(null, null, null);

        Assertions.assertEquals(interceptor.apply(builder).headers().size(), 0);
    }
}
