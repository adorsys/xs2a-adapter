package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.api.AccountApi;
import de.adorsys.xs2a.gateway.mapper.AccountListHolderMapper;
import de.adorsys.xs2a.gateway.model.ais.AccountListTO;
import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.AccountService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AccountController extends AbstractController implements AccountApi {
    private final AccountService accountService;
    private final AccountListHolderMapper accountListHolderMapper = Mappers.getMapper(AccountListHolderMapper.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(UUID xRequestID, String consentID, Boolean withBalance, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = Headers.builder()
                                  .xRequestId(xRequestID)
                                  .consentId(consentID)
                                  .digest(digest)
                                  .signature(signature)
                                  .tppSignatureCertificate(tpPSignatureCertificate)
                                  .psuIpAddress(psUIPAddress)
                                  .psuIpPort(psUIPPort)
                                  .psuAccept(psUAccept)
                                  .psuAcceptCharset(psUAcceptCharset)
                                  .psuAcceptEncoding(psUAcceptEncoding)
                                  .psuAcceptLanguage(psUAcceptLanguage)
                                  .psuUserAgent(psUUserAgent)
                                  .psuHttpMethod(psUHttpMethod)
                                  .psuDeviceId(psUDeviceID)
                                  .psuGeoLocation(psUGeoLocation)
                                  .build();

        RequestParams requestParams = RequestParams.builder()
                                              .withBalance(withBalance)
                                              .build();

        AccountListHolder accountListHolder = accountService.getAccountList(headers, requestParams);

        return ResponseEntity.status(HttpStatus.OK)
                       .body(accountListHolderMapper.toAccountListTO(accountListHolder));
    }
}
