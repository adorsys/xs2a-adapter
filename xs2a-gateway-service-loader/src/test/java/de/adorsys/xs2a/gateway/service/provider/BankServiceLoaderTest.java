package de.adorsys.xs2a.gateway.service.provider;

import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.impl.BankServiceLoader;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankServiceLoaderTest {

    private static final String BANK_CODE = "test";
    private final BankServiceLoader bankServiceLoader = new BankServiceLoader();

    @Test
    public void getPaymentInitiationService() {
        PaymentInitiationService paymentInitiationService = bankServiceLoader.getPaymentInitiationService(BANK_CODE);

        assertThat(paymentInitiationService).isNotNull();
    }

    @Test
    public void getAccountInformationService() {
        AccountInformationService accountInformationService = bankServiceLoader.getAccountInformationService(BANK_CODE);

        assertThat(accountInformationService).isNotNull();
    }

    @Test(expected = BankNotSupportedException.class)
    public void getAdapterManagerForUnsupportedBank() {
        bankServiceLoader.getPaymentInitiationService("unsupported-bank-code");
    }
}
