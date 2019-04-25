package de.adorsys.xs2a.gateway.service.provider;

import org.junit.Test;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

public class ProviderTest {

    private static final String BANK_CODE = "test";

    @Test
    public void getPaymentInitiationService() {
        PaymentInitiationServiceProvider provider = getPaymentInitiationServiceProvider(BANK_CODE);

        assertThat(provider).isNotNull();
    }

    @Test
    public void getAccountInformationService() {
        AccountInformationServiceProvider provider = getAccountInformationServiceProvider();

        assertThat(provider).isNotNull();
    }

    @Test(expected = BankNotSupportedException.class)
    public void getAdapterManagerForUnsupportedBank() {
        getPaymentInitiationServiceProvider("unsupported-bank-code");
    }

    private PaymentInitiationServiceProvider getPaymentInitiationServiceProvider(String bankCode) {
        ServiceLoader<PaymentInitiationServiceProvider> loader = ServiceLoader.load(PaymentInitiationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(pis -> pis.getBankCodes().contains(bankCode))
                       .findFirst()
                       .orElseThrow(() -> new BankNotSupportedException(bankCode));
    }

    private AccountInformationServiceProvider getAccountInformationServiceProvider() {
        ServiceLoader<AccountInformationServiceProvider> loader = ServiceLoader.load(AccountInformationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(ais -> ais.getBankCodes().contains(BANK_CODE))
                       .findFirst()
                       .orElseThrow(() -> new BankNotSupportedException(BANK_CODE));
    }
}