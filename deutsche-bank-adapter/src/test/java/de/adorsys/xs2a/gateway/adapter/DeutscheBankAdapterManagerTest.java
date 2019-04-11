package de.adorsys.xs2a.gateway.adapter;

import de.adorsys.xs2a.gateway.service.PaymentService;
import de.adorsys.xs2a.gateway.service.consent.ConsentService;
import de.adorsys.xs2a.gateway.service.impl.DeutscheBankConsentService;
import de.adorsys.xs2a.gateway.service.impl.DeutscheBankPaymentService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeutscheBankAdapterManagerTest {

    private AdapterManager manager;

    @Before
    public void setUp() {
        manager = new DeutscheBankAdapterManager();
    }

    @Test
    public void getConsentService() {
        ConsentService consentService = manager.getConsentService();
        assertThat(consentService).isInstanceOf(DeutscheBankConsentService.class);
    }

    @Test
    public void getPaymentService() {
        PaymentService paymentService = manager.getPaymentService();
        assertThat(paymentService).isInstanceOf(DeutscheBankPaymentService.class);
    }
}