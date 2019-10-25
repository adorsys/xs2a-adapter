package de.adorsys.xs2a.adapter.adorsys.service.provider;

import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import org.junit.Before;
import org.junit.Test;

import static de.adorsys.xs2a.adapter.adorsys.service.provider.OauthHeaderInterceptor.OAUTH_HEADER_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OauthHeaderInterceptorTest {

    private String header;
    private OauthHeaderInterceptor interceptor;

    @Before
    public void setUp() {
        header = AdapterConfig.readProperty(OAUTH_HEADER_NAME);
        interceptor = new OauthHeaderInterceptor();
    }

    @Test
    public void checkHeaderValueIsPreStep() {
        Request.Builder result = applyInterceptor("90001001");

        assertThat(result.headers().get(header), is("pre-step"));
    }

    @Test
    public void checkHeaderValueIsIntegrated() {
        Request.Builder result = applyInterceptor("90001002");

        assertThat(result.headers().get(header), is("integrated"));
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

        assertThat(interceptor.apply(builder).headers().size(), is(0));
    }
}
