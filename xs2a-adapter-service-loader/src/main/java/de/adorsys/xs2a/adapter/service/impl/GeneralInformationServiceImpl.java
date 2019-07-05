package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.Bank;
import de.adorsys.xs2a.adapter.service.GeneralInformationService;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class GeneralInformationServiceImpl implements GeneralInformationService {
    private final AdapterServiceLoader adapterServiceLoader;
    private final BankMapper bankMapper = Mappers.getMapper(BankMapper.class);

    public GeneralInformationServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public List<Bank> getSupportedBankList() {
        return bankMapper.toBanks(adapterServiceLoader.getSupportedAspsps());
    }
}
