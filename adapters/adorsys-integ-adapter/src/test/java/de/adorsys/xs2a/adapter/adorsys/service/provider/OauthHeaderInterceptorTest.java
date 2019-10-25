package de.adorsys.xs2a.adapter.adorsys.service.provider;

import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import org.junit.Test;

import static de.adorsys.xs2a.adapter.adorsys.service.provider.OauthHeaderInterceptor.OAUTH_HEADER_NAME;
import static de.adorsys.xs2a.adapter.adorsys.service.provider.OauthHeaderInterceptor.OAUTH_HEADER_VALUE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OauthHeaderInterceptorTest {

    @Test
    public void apply() {
        String header = AdapterConfig.readProperty(OAUTH_HEADER_NAME);
        String value = AdapterConfig.readProperty(OAUTH_HEADER_VALUE);

        RequestBuilderImpl builder = new RequestBuilderImpl(null, null, null);
        OauthHeaderInterceptor interceptor = new OauthHeaderInterceptor();

        // pre-step case
        builder.header(RequestHeaders.X_GTW_BANK_CODE, "90001001");
        assertThat(interceptor.apply(builder).headers().get(header), is("pre-step"));

        // integrated case
        builder.header(RequestHeaders.X_GTW_BANK_CODE, "90001002");
        assertThat(interceptor.apply(builder).headers().get(header), is("integrated"));
    }

    @Test
    public void applyRequestBankCodeIsAbsent() {

        RequestBuilderImpl builder = new RequestBuilderImpl(null, null, null);
        OauthHeaderInterceptor interceptor = new OauthHeaderInterceptor();

        assertThat(interceptor.apply(builder).headers().size(), is(0));
    }
}
