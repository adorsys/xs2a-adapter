package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.DkbAccessTokenService;
import de.adorsys.xs2a.adapter.service.impl.DkbAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.DkbPaymentInitiationService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DkbServiceProviderTest {

    private DkbServiceProvider provider;

    @Before
    public void setUp() {
        // these are required properties for correct working of dkb adapter
        System.setProperty(DkbAccessTokenService.DKB_TOKEN_CONSUMER_KEY_PROPERTY, "111");
        System.setProperty(DkbAccessTokenService.DKB_TOKEN_CONSUMER_SECRET_PROPERTY, "111");

        provider = new DkbServiceProvider();
    }

    @Test
    public void getPaymentInitiationService() {
        PaymentInitiationService service = provider.getPaymentInitiationService(null);

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOfAny(DkbPaymentInitiationService.class);
    }

    @Test
    public void getAccountInformationService() {
        AccountInformationService service = provider.getAccountInformationService(null);

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOfAny(DkbAccountInformationService.class);
    }
}
