package de.adorsys.xs2a.adapter.dkb;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DkbServiceProviderTest {

    private DkbServiceProvider provider;
    private HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);

    @BeforeEach
    public void setUp() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);

        provider = new DkbServiceProvider();
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService service = provider.getPaymentInitiationService(null, httpClientFactory, null, null);

        assertThat(service)
            .isNotNull()
            .isInstanceOfAny(DkbPaymentInitiationService.class);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService service = provider.getAccountInformationService(null, httpClientFactory, null, null);

        assertThat(service)
            .isNotNull()
            .isInstanceOfAny(DkbAccountInformationService.class);
    }
}
