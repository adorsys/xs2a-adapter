package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.Bank;
import de.adorsys.xs2a.adapter.service.GeneralInformationService;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.ServiceProvider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneralInformationServiceImpl implements GeneralInformationService {
    private final BankServiceLoader bankServiceLoader;

    public GeneralInformationServiceImpl(BankServiceLoader bankServiceLoader) {
        this.bankServiceLoader = bankServiceLoader;
    }

    @Override
    public List<Bank> getSupportedBankList() {
        List<Bank> banksWithAccountServiceSupport = getSupportedBanksByProvider(AccountInformationServiceProvider.class);
        List<Bank> banksWithPaymentServiceSupport = getSupportedBanksByProvider(PaymentInitiationServiceProvider.class);

        return joinBankListsWithoutDuplicates(banksWithAccountServiceSupport, banksWithPaymentServiceSupport);
    }

    private <T extends ServiceProvider> List<Bank> getSupportedBanksByProvider(Class<T> providerClass) {
        return bankServiceLoader.getSupportedServiceProviders(providerClass)
                   .stream()
                   .map(provider -> new Bank(provider.getBankName(), provider.getBankCodes()))
                   .collect(Collectors.toList());
    }

    private List<Bank> joinBankListsWithoutDuplicates(List<Bank> firstList, List<Bank> secondList) {
        return Stream.of(firstList, secondList)
                   .flatMap(List::stream)
                   .distinct()
                   .collect(Collectors.toList());
    }
}
