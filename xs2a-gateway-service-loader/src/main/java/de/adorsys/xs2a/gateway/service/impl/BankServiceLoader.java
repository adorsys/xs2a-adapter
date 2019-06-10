package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.exception.BankCodeNotProvidedException;
import de.adorsys.xs2a.gateway.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.provider.BankServiceProvider;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BankServiceLoader {

    public List<BankServiceProvider> getBankServiceProviderList() {
        ServiceLoader<BankServiceProvider> loader = ServiceLoader.load(BankServiceProvider.class);

        return StreamSupport.stream(loader.spliterator(), false)
                   .collect(Collectors.toList());
    }

    public AccountInformationService getAccountInformationService(String bankCode) {
        if (bankCode == null) {
            throw new BankCodeNotProvidedException();
        }

        return getBankServiceProvider(bankCode)
                   .getAccountInformationService();
    }

    public PaymentInitiationService getPaymentInitiationService(String bankCode) {
        if (bankCode == null) {
            throw new BankCodeNotProvidedException();
        }

        return getBankServiceProvider(bankCode)
                   .getPaymentInitiationService();
    }

    private BankServiceProvider getBankServiceProvider(String bankCode) {
        ServiceLoader<BankServiceProvider> loader = ServiceLoader.load(BankServiceProvider.class);

        return StreamSupport.stream(loader.spliterator(), false)
                   .filter(pis -> pis.getBankCodes().contains(bankCode))
                   .findFirst()
                   .orElseThrow(() -> new BankNotSupportedException(bankCode));
    }
}
