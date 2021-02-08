package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrealogixServiceProviderTest {

    private final HttpClientFactory clientFactory = mock(HttpClientFactory.class);
    private final HttpClientConfig clientConfig = mock(HttpClientConfig.class);
    private final DkbServiceProvider serviceProvider = new DkbServiceProvider();
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        when(clientFactory.getHttpClientConfig()).thenReturn(clientConfig);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService =
            serviceProvider.getAccountInformationService(aspsp, clientFactory, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(BaseAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService =
            serviceProvider.getPaymentInitiationService(aspsp, clientFactory, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(CrealogixPaymentInitiationService.class);
    }
}
