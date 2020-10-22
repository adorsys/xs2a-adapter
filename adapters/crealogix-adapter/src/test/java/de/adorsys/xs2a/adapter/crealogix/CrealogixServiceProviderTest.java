package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CrealogixServiceProviderTest {

    private final HttpClientFactory clientFactory = mock(HttpClientFactory.class);
    private final CrealogixServiceProvider serviceProvider = new CrealogixServiceProvider();

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService =
            serviceProvider.getAccountInformationService(null, clientFactory, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(BaseAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService =
            serviceProvider.getPaymentInitiationService(null, clientFactory, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(CrealogixPaymentInitiationService.class);
    }
}
