package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.BankMapper;
import de.adorsys.xs2a.adapter.model.BankTO;
import de.adorsys.xs2a.adapter.service.impl.AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.impl.AspspAdapterConfigRecord;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneralInformationController {
    private AdapterServiceLoader adapterServiceLoader;
    private final BankMapper bankMapper = Mappers.getMapper(BankMapper.class);

    public GeneralInformationController(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @RequestMapping(
        value = "/v1/supported-banks",
        method = RequestMethod.GET
    )
    public ResponseEntity<List<BankTO>> getSupportedBankList() {
        List<AspspAdapterConfigRecord> supportedAspsps = adapterServiceLoader.getSupportedAspsps();

        return ResponseEntity.ok(bankMapper.toBankTOList(supportedAspsps));
    }
}
