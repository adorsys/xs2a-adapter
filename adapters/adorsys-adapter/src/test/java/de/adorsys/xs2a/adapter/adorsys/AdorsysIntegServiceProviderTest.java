package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdorsysIntegServiceProviderTest {

    private AdorsysIntegServiceProvider provider;
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        provider = spy(new AdorsysIntegServiceProvider());
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = provider.getPaymentInitiationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isExactlyInstanceOf(BasePaymentInitiationService.class);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = provider.getAccountInformationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isExactlyInstanceOf(AdorsysAccountInformationService.class);
    }

    @Test
    void getService_checkInterceptors() {
        ArgumentCaptor<Interceptor> interceptorCaptor = ArgumentCaptor.forClass(Interceptor.class);
        Pkcs12KeyStore mockedKeyStore = mock(Pkcs12KeyStore.class);

        when(httpClientConfig.getKeyStore()).thenReturn(mockedKeyStore);

        provider.getAccountInformationService(aspsp, httpClientFactory, null);

        verify(provider, times(1)).getInterceptors(any(), interceptorCaptor.capture());

        Interceptor[] actualAdorsysInterceptors = interceptorCaptor.getAllValues().toArray(new Interceptor[0]);

        assertThat(actualAdorsysInterceptors)
            .hasSize(2)
            .matches(ints -> ints[0] instanceof OauthHeaderInterceptor)
            .matches(ints -> ints[1] instanceof AdorsysSigningHeadersInterceptor);
    }

    @Test
    void getOauth2Service() {
        Oauth2Service actualService
            = provider.getOauth2Service(aspsp, httpClientFactory);

        assertThat(actualService)
            .isExactlyInstanceOf(AdorsysIntegOauth2Service.class);
    }
}
