package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SparkasseServiceProviderTest {

    private final HttpClientFactory clientFactory = mock(HttpClientFactory.class);
    private final SparkasseServiceProvider provider
        = new SparkasseServiceProvider();

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = provider.getAccountInformationService(null, clientFactory, null, null);

        assertThat(actualService).isInstanceOf(BaseAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = provider.getPaymentInitiationService(null, clientFactory, null, null);

        assertThat(actualService).isInstanceOf(SparkassePaymentInitiationService.class);
    }

    @Test
    void getOauth2Service() {
        Oauth2Service actualService
            = provider.getOauth2Service(null, clientFactory, null);

        assertThat(actualService).isInstanceOf(SparkasseOauth2Service.class);
    }
}
