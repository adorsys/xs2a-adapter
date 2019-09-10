package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.psd2.Psd2AccountInformationMapper;
import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import de.adorsys.xs2a.adapter.service.psd2.model.TransactionsResponse;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.singletonMap;

@RestController
public class Psd2AccountInformationController implements Psd2AccountInformationApi {

    private final Psd2AccountInformationService accountInformationService;
    private final HeadersMapper headersMapper;
    private final Psd2AccountInformationMapper mapper = Mappers.getMapper(Psd2AccountInformationMapper.class);

    public Psd2AccountInformationController(Psd2AccountInformationService accountInformationService,
                                            HeadersMapper headersMapper) {
        this.accountInformationService = accountInformationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<ConsentsResponseTO> createConsent(Map<String, String> headers, ConsentsTO body) {

        Response<ConsentsResponse> response = accountInformationService.createConsent(headers, mapper.toConsents(body));
        ConsentsResponse consentsResponse = response.getBody();
        if (consentsResponse.getConsentId() == null &&  consentsResponse.getLinks() == null) {
            consentsResponse.setLinks(
                singletonMap("oauthConsent", new HrefType(Oauth2Controller.AUTHORIZATION_REQUEST_URI))
            );
        }
        return toResponseEntity(response, mapper::toConsentsResponseTO);
    }

    private <T, U> ResponseEntity<U> toResponseEntity(Response<T> response,
                                                      Function<? super T, ? extends U> bodyMapper) {
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(bodyMapper.apply(response.getBody()));
    }

    @Override
    public ResponseEntity<ConsentInformationResponseTO> getConsentInformation(String consentId,
                                                                              Map<String, String> headers) {
        return toResponseEntity(
            accountInformationService.getConsentInformation(consentId, headers),
            mapper::toConsentInformationResponseTO
        );
    }

    @Override
    public ResponseEntity<Void> deleteConsent(String consentId, Map<String, String> headers) {
        Response<Void> response = accountInformationService.deleteConsent(consentId, headers);
        return ResponseEntity.noContent()
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .build();
    }

    @Override
    public ResponseEntity<ConsentStatusResponseTO> getConsentStatus(String consentId, Map<String, String> headers) {
        return toResponseEntity(
            accountInformationService.getConsentStatus(consentId, headers),
            mapper::toConsentStatusResponseTO
        );
    }

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startConsentAuthorisation(String consentId,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        return toResponseEntity(
            accountInformationService.startConsentAuthorisation(consentId, headers, mapper.map(body)),
            mapper::map
        );
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId,
                                                                   String authorisationId,
                                                                   Map<String, String> headers) {
        return toResponseEntity(
            accountInformationService.getConsentScaStatus(consentId, authorisationId, headers),
            mapper::toScaStatusResponseTO
        );
    }

    @Override
    public ResponseEntity<UpdateAuthorisationResponseTO> updateConsentsPsuData(String consentId,
                                                        String authorisationId,
                                                        Map<String, String> headers,
                                                        UpdateAuthorisationTO body) {
        return toResponseEntity(
            accountInformationService.updateConsentsPsuData(consentId, authorisationId, headers, mapper.map(body)),
            mapper::map
        );
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(Map<String, String> queryParameters, Map<String, String> headers) throws IOException {
        return toResponseEntity(
            accountInformationService.getAccounts(queryParameters, headers),
            mapper::toAccountListTO
        );
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponseTO> getBalances(String accountId, Map<String, String> queryParameters, Map<String, String> headers) throws IOException {
        return toResponseEntity(
            accountInformationService.getBalances(accountId, queryParameters, headers),
            mapper::toReadAccountBalanceResponseTO
        );
    }

    @Override
    public ResponseEntity<?> getTransactionList(String accountId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException {
        Response<?> response = accountInformationService.getTransactions(accountId, queryParameters, headers);
        if (response.getBody() instanceof TransactionsResponse) {
            return toResponseEntity(
                (Response<TransactionsResponse>) response,
                mapper::toTransactionsResponseTO
            );
        }
        return toResponseEntity(response, Function.identity());
    }
}
