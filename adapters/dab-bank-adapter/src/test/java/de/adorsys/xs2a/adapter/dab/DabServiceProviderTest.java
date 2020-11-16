package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DabServiceProviderTest {

    private final HttpClientFactory factory = mock(HttpClientFactory.class);
    private final DabServiceProvider provider = new DabServiceProvider();

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = provider.getAccountInformationService(null, factory, null, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(BaseAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = provider.getPaymentInitiationService(null, factory, null, null, null);

        assertThat(actualService)
            .isNotNull()
            .isInstanceOf(BasePaymentInitiationService.class);
    }
}
