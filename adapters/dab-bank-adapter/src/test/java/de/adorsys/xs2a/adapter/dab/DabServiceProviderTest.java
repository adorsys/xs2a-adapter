package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DabServiceProviderTest {

    private final HttpClientFactory factory = mock(HttpClientFactory.class);
    private final HttpClientConfig config = mock(HttpClientConfig.class);
    private final DabServiceProvider provider = new DabServiceProvider();

    @BeforeEach
    void setUp() {
        when(factory.getHttpClientConfig()).thenReturn(config);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = provider.getAccountInformationService(null, factory, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(DavAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = provider.getPaymentInitiationService(null, factory, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(BasePaymentInitiationService.class);
    }
}
