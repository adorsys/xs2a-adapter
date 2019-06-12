package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.exception.BankCodeNotProvidedException;
import de.adorsys.xs2a.gateway.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.gateway.service.provider.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.gateway.service.provider.ServiceProvider;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BankServiceLoader {

    public AccountInformationService getAccountInformationService(String bankCode) {
        if (bankCode == null) {
            throw new BankCodeNotProvidedException();
        }

        ServiceLoader<AccountInformationServiceProvider> loader =
            ServiceLoader.load(AccountInformationServiceProvider.class);

        return StreamSupport.stream(loader.spliterator(), false)
                   .filter(pis -> pis.getBankCodes().contains(bankCode))
                   .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                   .getAccountInformationService();
    }

    public PaymentInitiationService getPaymentInitiationService(String bankCode) {
        if (bankCode == null) {
            throw new BankCodeNotProvidedException();
        }

        ServiceLoader<PaymentInitiationServiceProvider> loader =
            ServiceLoader.load(PaymentInitiationServiceProvider.class);

        return StreamSupport.stream(loader.spliterator(), false)
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
