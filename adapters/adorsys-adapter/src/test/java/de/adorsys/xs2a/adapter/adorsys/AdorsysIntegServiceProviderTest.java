package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AdorsysIntegServiceProviderTest {

    private AdorsysIntegServiceProvider provider;
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);

    @BeforeEach
    void setUp() {
        provider = new AdorsysIntegServiceProvider();
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = provider.getPaymentInitiationService(null, httpClientFactory, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(BasePaymentInitiationService.class);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = provider.getAccountInformationService(null, httpClientFactory, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(AdorsysAccountInformationService.class);
    }

    @Test
    void getOauth2Service() {
        Oauth2Service actualService
            = provider.getOauth2Service(null, httpClientFactory, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(AdorsysIntegOauth2Service.class);
    }
}
