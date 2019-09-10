package de.adorsys.xs2a.tpp.controller;

import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import de.adorsys.xs2a.adapter.api.remote.psd2.Psd2AccountInformationClient;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class Psd2AccountInformationController implements Psd2AccountInformationApi {

    private final Psd2AccountInformationClient client;

    public Psd2AccountInformationController(Psd2AccountInformationClient client) {
        this.client = client;
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(Map<String, String> queryParameters,
                                                        Map<String, String> headers) throws IOException {
        return client.getAccountList(queryParameters, headers);
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponseTO> getBalances(String accountId,
                                                                    Map<String, String> queryParameters,
                                                                    Map<String, String> headers) throws IOException {
        return client.getBalances(accountId, queryParameters, headers);
    }

    @Override
    public ResponseEntity<?> getTransactionList(String accountId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException {
        return client.getTransactionList(accountId, queryParameters, headers);
    }

    @Override
    public ResponseEntity<ConsentsResponseTO> createConsent(Map<String, String> headers, ConsentsTO body) {
        return client.createConsent(headers, body);
    }

    @Override
    public ResponseEntity<ConsentInformationResponseTO> getConsentInformation(String consentId,
                                                                              Map<String, String> headers) {
        return client.getConsentInformation(consentId, headers);
    }

    @Override
    public ResponseEntity<Void> deleteConsent(String consentId, Map<String, String> headers) {
        return client.deleteConsent(consentId, headers);
    }

    @Override
    public ResponseEntity<ConsentStatusResponseTO> getConsentStatus(String consentId, Map<String, String> headers) {
        return client.getConsentStatus(consentId, headers);
    }

    @Override
    public ResponseEntity<StartScaProcessResponseTO> startConsentAuthorisation(String consentId,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        return client.startConsentAuthorisation(consentId, headers, body);
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId,
                                                                   String authorisationId,
                                                                   Map<String, String> headers) {
        return client.getConsentScaStatus(consentId, authorisationId, headers);
    }

    @Override
    public ResponseEntity<UpdateAuthorisationResponseTO> updateConsentsPsuData(String consentId,
                                                                               String authorisationId,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        return client.updateConsentsPsuData(consentId, authorisationId, headers, body);
    }
}
