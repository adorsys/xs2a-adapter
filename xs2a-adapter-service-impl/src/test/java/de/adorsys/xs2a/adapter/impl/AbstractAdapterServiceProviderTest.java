package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AbstractAdapterServiceProviderTest {

    private AbstractAdapterServiceProvider provider;
    private Aspsp aspsp;

    @BeforeEach
    void setUp() {
        provider = new AbstractAdapterServiceProvider() {
            @Override
            public AccountInformationService getAccountInformationService(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore, LinksRewriter linksRewriter) {
                return null;
            }

            @Override
            public AccountInformationService getAccountInformationService(Aspsp aspsp, HttpClientFactory httpClientFactory, LinksRewriter linksRewriter) {
                return null;
            }

            @Override
            public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore, LinksRewriter linksRewriter) {
                return null;
            }

            @Override
            public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp, HttpClientFactory httpClientFactory, LinksRewriter linksRewriter) {
                return null;
            }

            @Override
            public String getAdapterId() {
                return null;
            }
        };
        aspsp = new Aspsp();
    }

    @Test
    void getInterceptors_wiremockValidationDisabled_InterceptorIsNull() {
        provider.wiremockValidationEnabled(false);
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors).isEqualTo(Collections.emptyList());
    }

    @Test
    void getInterceptors_wiremockValidationDisabled_InterceptorIsNotNull() {
        provider.wiremockValidationEnabled(false);
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, builder -> null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors.size()).isEqualTo(1);
    }

    @Test
    void getInterceptors_wiremockValidationEnabled_InterceptorIsNotNull_wrongAdapterId() {
        provider.wiremockValidationEnabled(true);
        aspsp.setAdapterId("wrong-adapter-id");
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, builder -> null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors.size()).isEqualTo(1);
    }

    @Disabled("bug - returns 'false' even if path exists, https://jira.adorsys.de/browse/XS2AAD-783")
    @Test
    void getInterceptors_wiremockValidationEnabled_InterceptorIsNotNull_WiremockStubInterceptor() {
        provider.wiremockValidationEnabled(true);
        aspsp.setAdapterId("adorsys-adapter");
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, builder -> null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors.size()).isEqualTo(2);
    }
}
