package de.adorsys.xs2a.gateway.service.provider;

import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.DkbAccessTokenService;
import de.adorsys.xs2a.gateway.service.impl.DkbAccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.DkbPaymentInitiationService;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

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
    public void getBankCodes() {
        Set<String> codes = provider.getBankCodes();

        assertThat(codes).hasSize(1);
        assertThat(codes.contains("12030000")).isTrue();
    }

    @Test
    public void getPaymentInitiationService() {
        PaymentInitiationService service = provider.getPaymentInitiationService();

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOfAny(DkbPaymentInitiationService.class);
    }

    @Test
    public void getAccountInformationService() {
        AccountInformationService service = provider.getAccountInformationService();

        assertThat(service).isNotNull();
        assertThat(service).isInstanceOfAny(DkbAccountInformationService.class);
    }
}