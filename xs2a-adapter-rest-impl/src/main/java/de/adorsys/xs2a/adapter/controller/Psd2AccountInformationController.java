package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.rest.mapper.Psd2AccountInformationMapper;
import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import de.adorsys.xs2a.adapter.service.psd2.model.TransactionsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
public class Psd2AccountInformationController implements Psd2AccountInformationApi {

    private final Psd2AccountInformationService accountInformationService;
    private final Psd2AccountInformationMapper mapper;

    public Psd2AccountInformationController(Psd2AccountInformationService accountInformationService,
                                            Psd2AccountInformationMapper mapper) {
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
                                                                               UpdateAuthorisationTO body) {


        return ResponseEntity.ok(mapper.map(
            accountInformationService.startConsentAuthorisation(consentId, headers, mapper.map(body))
        ));
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
    public ResponseEntity<UpdateAuthorisationResponseTO> updateConsentsPsuData(String consentId,
                                                        String authorisationId,
                                                        Map<String, String> headers,
                                                        UpdateAuthorisationTO body) {
        return ResponseEntity.ok(mapper.map(
            accountInformationService.updateConsentsPsuData(consentId, authorisationId, headers, mapper.map(body))
        ));
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
    public ResponseEntity<?> getTransactionList(String accountId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException {
        Object transactions = accountInformationService.getTransactions(accountId, queryParameters, headers);
        if (transactions instanceof TransactionsResponse) {
            return ResponseEntity.ok(mapper.toTransactionsResponseTO((TransactionsResponse) transactions));
        }
        return ResponseEntity.ok(transactions);
    }
}
