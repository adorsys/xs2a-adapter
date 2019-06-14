package de.adorsys.xs2a.gateway.api;

import de.adorsys.xs2a.gateway.model.BankTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface GeneralInformationApi {

    @RequestMapping(
        value = "/v1/supported-banks",
        method = RequestMethod.GET
    )
    ResponseEntity<List<BankTO>> getSupportedBankList();
}
