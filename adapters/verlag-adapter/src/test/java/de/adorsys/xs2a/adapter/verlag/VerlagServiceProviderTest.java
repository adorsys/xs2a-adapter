package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerlagServiceProviderTest {

    private VerlagServiceProvider serviceProvider;
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        serviceProvider = new VerlagServiceProvider();
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = serviceProvider.getAccountInformationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isInstanceOf(VerlagAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = serviceProvider.getPaymentInitiationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isInstanceOf(VerlagPaymentInitiationService.class);
    }

    @Test
    void getDownloadService() {
        DownloadService actualService
            = serviceProvider.getDownloadService("", httpClientFactory);

        assertThat(actualService)
            .isInstanceOf(BaseDownloadService.class);
    }
}
