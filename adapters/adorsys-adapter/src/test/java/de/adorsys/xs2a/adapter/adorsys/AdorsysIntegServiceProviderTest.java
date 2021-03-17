package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdorsysIntegServiceProviderTest {

    private AdorsysIntegServiceProvider provider;
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        provider = new AdorsysIntegServiceProvider();
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
    void getOauth2Service() {
        Oauth2Service actualService
            = provider.getOauth2Service(aspsp, httpClientFactory);

        assertThat(actualService)
            .isExactlyInstanceOf(AdorsysIntegOauth2Service.class);
    }
}
