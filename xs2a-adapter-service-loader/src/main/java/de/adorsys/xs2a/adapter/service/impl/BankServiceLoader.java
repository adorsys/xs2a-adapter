package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.exception.BankCodeNotProvidedException;
import de.adorsys.xs2a.adapter.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.ServiceProvider;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BankServiceLoader {

    private ServiceLoader<AccountInformationServiceProvider> accountInformationServiceLoader;
    private ServiceLoader<PaymentInitiationServiceProvider> paymentInitiationServiceloader;

    public BankServiceLoader() {
        accountInformationServiceLoader = ServiceLoader.load(AccountInformationServiceProvider.class);
        paymentInitiationServiceloader = ServiceLoader.load(PaymentInitiationServiceProvider.class);
    }

    public AccountInformationService getAccountInformationService(String bankCode) {
        if (bankCode == null) {
            throw new BankCodeNotProvidedException();
        }

        return StreamSupport.stream(accountInformationServiceLoader.spliterator(), false)
                   .filter(pis -> pis.getBankCodes().contains(bankCode))
                   .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                   .getAccountInformationService();
    }

    public PaymentInitiationService getPaymentInitiationService(String bankCode) {
        if (bankCode == null) {
            throw new BankCodeNotProvidedException();
        }

        return StreamSupport.stream(paymentInitiationServiceloader.spliterator(), false)
                   .filter(pis -> pis.getBankCodes().contains(bankCode))
                   .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                   .getPaymentInitiationService();
    }

    public <T extends ServiceProvider> List<T> getSupportedServiceProviders(Class<T> providerClass) {
        ServiceLoader<T> loader = ServiceLoader.load(providerClass);

        return StreamSupport.stream(loader.spliterator(), false)
                   .collect(Collectors.toList());
    }
}
