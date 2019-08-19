package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.rest.mapper.AccountInformationMapper;
import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
public class Psd2AccountInformationController implements Psd2AccountInformationApi {

    private final Psd2AccountInformationService accountInformationService;
    private final AccountInformationMapper mapper;

    public Psd2AccountInformationController(Psd2AccountInformationService accountInformationService,
                                            AccountInformationMapper mapper) {
        this.accountInformationService = accountInformationService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<ConsentsResponseTO> createConsent(Map<String, String> headers, ConsentsTO body) {

        ConsentsResponse consentsResponse = accountInformationService.createConsent(headers, mapper.toConsents(body));
        if (consentsResponse.getConsentId() == null &&  consentsResponse.getLinks() == null) {
            consentsResponse.setLinks(
                singletonMap("oauthConsent", new HrefType(Oauth2Controller.AUTHORIZATION_REQUEST_URI))
            );
        }
        return ResponseEntity.ok(mapper.toConsentsResponseTO(consentsResponse));
    }

    @Override
    public ResponseEntity<ConsentInformationResponseTO> getConsentInformation(String consentId,
                                                                              Map<String, String> headers) {
        return ResponseEntity.ok(mapper.toConsentInformationResponseTO(
            accountInformationService.getConsentInformation(consentId, headers)
        ));
    }

    @Override
    public ResponseEntity<Void> deleteConsent(String consentId, Map<String, String> headers) {
        accountInformationService.deleteConsent(consentId, headers);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ConsentStatusResponseTO> getConsentStatus(String consentId, Map<String, String> headers) {
        return ResponseEntity.ok(mapper.toConsentStatusResponseTO(
            accountInformationService.getConsentStatus(consentId, headers)
        ));
    }

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startConsentAuthorisation(String consentId,
                                                                               Map<String, String> headers,
                                                                               ObjectNode body) {
        throw new UnsupportedOperationException(); // todo https://git.adorsys.de/xs2a-gateway/xs2a-gateway/issues/328
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId,
                                                                   String authorisationId,
                                                                   Map<String, String> headers) {
        return ResponseEntity.ok(mapper.toScaStatusResponseTO(
            accountInformationService.getConsentScaStatus(consentId, authorisationId, headers)
        ));
    }

    @Override
    public ResponseEntity<Object> updateConsentsPsuData(String consentId,
                                                        String authorisationId,
                                                        Map<String, String> headers,
                                                        ObjectNode body) {
        throw new UnsupportedOperationException(); // todo https://git.adorsys.de/xs2a-gateway/xs2a-gateway/issues/328
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(Map<String, String> queryParameters, Map<String, String> headers) throws IOException {
        return ResponseEntity.ok(mapper.toAccountListTO(
            accountInformationService.getAccounts(queryParameters, headers)
        ));
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponseTO> getBalances(String accountId, Map<String, String> queryParameters, Map<String, String> headers) throws IOException {
        return ResponseEntity.ok(mapper.toReadAccountBalanceResponseTO(
            accountInformationService.getBalances(accountId, queryParameters, headers)
        ));
    }

    @Override
    public ResponseEntity<TransactionsResponseTO> getTransactionList(String accountId,
                                                                     Map<String, String> queryParameters,
                                                                     Map<String, String> headers) throws IOException {
        return ResponseEntity.ok(mapper.toTransactionsResponseTO(
            accountInformationService.getTransactions(accountId, queryParameters, headers)
        ));
    }
}
