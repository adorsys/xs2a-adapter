package de.adorsys.xs2a.tpp.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.psd2.Psd2AccountInformationMapper;
import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @deprecated
 * This controller is no longer acceptable and will be removed in future releases.
 * <p>Use {@link ConsentController} instead.</p>
 */
@Deprecated
@RestController
public class Psd2AccountInformationController implements Psd2AccountInformationApi {

    private final Psd2AccountInformationService accountInformationService;
    private final HeadersMapper headersMapper;
    private final Psd2AccountInformationMapper accountInformationMapper
        = Mappers.getMapper(Psd2AccountInformationMapper.class);

    public Psd2AccountInformationController(Psd2AccountInformationService accountInformationService,
                                            HeadersMapper headersMapper) {
        this.accountInformationService = accountInformationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(Map<String, String> queryParameters,
                                                        Map<String, String> headers) throws IOException {
        Response<AccountList> response = accountInformationService.getAccounts(queryParameters, headers);

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toAccountListTO(response.getBody()));
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponseTO> getBalances(String accountId,
                                                                    Map<String, String> queryParameters,
                                                                    Map<String, String> headers) throws IOException {
        Response<ReadAccountBalanceResponse> response
            = accountInformationService.getBalances(accountId, queryParameters, headers);

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toReadAccountBalanceResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<?> getTransactionList(String accountId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException {
        Response<?> response = accountInformationService.getTransactions(accountId, queryParameters, headers);
        Object responseBody = response.getBody();

        if (responseBody instanceof TransactionsResponse) {
            return ResponseEntity.status(response.getStatusCode())
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(accountInformationMapper.toTransactionsResponseTO((TransactionsResponse) responseBody));
        }

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(responseBody);
    }

    @Override
    public ResponseEntity<ConsentsResponseTO> createConsent(Map<String, String> headers, ConsentsTO body) {
        Response<ConsentsResponse> response
            = accountInformationService.createConsent(headers, accountInformationMapper.toConsents(body));

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toConsentsResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<ConsentInformationResponseTO> getConsentInformation(String consentId,
                                                                              Map<String, String> headers) {
        Response<ConsentInformationResponse> response
            = accountInformationService.getConsentInformation(consentId, headers);

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toConsentInformationResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<Void> deleteConsent(String consentId, Map<String, String> headers) {
        Response<Void> response = accountInformationService.deleteConsent(consentId, headers);

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(response.getBody());
    }

    @Override
    public ResponseEntity<ConsentStatusResponseTO> getConsentStatus(String consentId, Map<String, String> headers) {
        Response<ConsentStatusResponse> response = accountInformationService.getConsentStatus(consentId, headers);

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toConsentStatusResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<StartScaProcessResponseTO> startConsentAuthorisation(String consentId,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        Response<StartScaProcessResponse> response = accountInformationService.startConsentAuthorisation(
            consentId, headers,
            accountInformationMapper.toUpdateAuthorisation(body)
        );

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toStartScaProcessResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId,
                                                                   String authorisationId,
                                                                   Map<String, String> headers) {
        Response<ScaStatusResponse> response
            = accountInformationService.getConsentScaStatus(consentId, authorisationId, headers);

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toScaStatusResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<UpdateAuthorisationResponseTO> updateConsentsPsuData(String consentId,
                                                                               String authorisationId,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        Response<UpdateAuthorisationResponse> response = accountInformationService.updateConsentsPsuData(
            consentId, authorisationId, headers,
            accountInformationMapper.toUpdateAuthorisation(body)
        );

        return ResponseEntity.status(response.getStatusCode())
                   .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                   .body(accountInformationMapper.toUpdateAuthorisationResponseTO(response.getBody()));
    }
}
