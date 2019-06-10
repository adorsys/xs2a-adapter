package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Bank;
import de.adorsys.xs2a.gateway.service.GeneralInformationService;

import java.util.List;

public class GeneralInformationServiceImpl implements GeneralInformationService {
    private final BankServiceLoader bankServiceLoader;

    public GeneralInformationServiceImpl(BankServiceLoader bankServiceLoader) {
        this.bankServiceLoader = bankServiceLoader;
    }

    @Override
    public List<Bank> getSupportedBankList() {
        throw new UnsupportedOperationException();
    }
}
