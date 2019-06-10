package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Bank;
import de.adorsys.xs2a.gateway.service.GeneralInformationService;

import java.util.List;
import java.util.stream.Collectors;

public class GeneralInformationServiceImpl implements GeneralInformationService {
    private final BankServiceLoader bankServiceLoader;

    public GeneralInformationServiceImpl(BankServiceLoader bankServiceLoader) {
        this.bankServiceLoader = bankServiceLoader;
    }

    @Override
    public List<Bank> getSupportedBankList() {
        return bankServiceLoader.getBankServiceProviderList().stream()
                   .map(provider -> new Bank(provider.getBankName(), provider.getBankCodes()))
                   .collect(Collectors.toList());
    }
}
