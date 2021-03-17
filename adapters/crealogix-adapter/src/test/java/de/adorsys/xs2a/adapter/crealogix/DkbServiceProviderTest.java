package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DkbServiceProviderTest {

    private final DkbServiceProvider provider = new DkbServiceProvider();
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService service = provider.getAccountInformationService(aspsp, httpClientFactory, null);

        assertThat(service)
            .isNotNull()
            .isExactlyInstanceOf(CrealogixAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService service = provider.getPaymentInitiationService(aspsp, httpClientFactory, null);

        assertThat(service)
            .isNotNull()
            .isExactlyInstanceOf(CrealogixPaymentInitiationService.class);
    }

    @Test
    void getAdapterId() {
        String actual = provider.getAdapterId();
        assertThat(actual).isEqualTo("dkb-adapter");
    }

    @Test
    void getEmbeddedPreAuthorisationService() {
        EmbeddedPreAuthorisationService service = provider.getEmbeddedPreAuthorisationService(aspsp, httpClientFactory);

        assertThat(service)
            .isNotNull()
            .isExactlyInstanceOf(CrealogixEmbeddedPreAuthorisationService.class);
    }
}
