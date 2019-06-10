package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.api.GeneralInformationApi;
import de.adorsys.xs2a.gateway.mapper.BankMapper;
import de.adorsys.xs2a.gateway.model.BankTO;
import de.adorsys.xs2a.gateway.service.Bank;
import de.adorsys.xs2a.gateway.service.GeneralInformationService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneralInformationController implements GeneralInformationApi {
    private final GeneralInformationService generalInformationService;
    private final BankMapper bankMapper = Mappers.getMapper(BankMapper.class);

    public GeneralInformationController(GeneralInformationService generalInformationService) {
        this.generalInformationService = generalInformationService;
    }

    @Override
    public ResponseEntity<List<BankTO>> getSupportedBankList() {
        List<Bank> supportedBanks = generalInformationService.getSupportedBankList();

        return ResponseEntity.ok(bankMapper.toBankTOList(supportedBanks));
    }
}
