package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.impl.DkbAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.DkbPaymentInitiationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class DkbServiceProviderTest {

    private DkbServiceProvider provider;
    private HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);

    @BeforeEach
    public void setUp() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);

        provider = new DkbServiceProvider();
    }

    @Test
    public void getPaymentInitiationService() {
        PaymentInitiationService service = provider.getPaymentInitiationService(null, httpClientFactory, null, null);

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOfAny(DkbPaymentInitiationService.class);
    }

    @Test
    public void getAccountInformationService() {
        AccountInformationService service = provider.getAccountInformationService(null, httpClientFactory, null, null);

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOfAny(DkbAccountInformationService.class);
    }
}
