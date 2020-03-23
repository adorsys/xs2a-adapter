package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.psd2.Psd2AccountInformationMapper;
import de.adorsys.xs2a.adapter.rest.psd2.Psd2AccountInformationApi;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
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

import static de.adorsys.xs2a.adapter.service.ResponseHeaders.emptyResponseHeaders;
import static java.util.Collections.singletonMap;

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
    private final Psd2AccountInformationMapper mapper = Mappers.getMapper(Psd2AccountInformationMapper.class);

    public Psd2AccountInformationController(Psd2AccountInformationService accountInformationService,
                                            HeadersMapper headersMapper) {
        this.accountInformationService = accountInformationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<ConsentsResponseTO> createConsent(Map<String, String> queryParameters,
                                                            Map<String, String> headers,
                                                            ConsentsTO body) {

        try {
            return createConsent0(queryParameters, headers, body);
        } catch (ErrorResponseException ex) {
            if (ex.getStatusCode() == 403 && ex.getMessage() != null && ex.getMessage().contains("TOKEN_INVALID")) {
                ConsentsResponse consentsResponse = new ConsentsResponse();
                consentsResponse.setLinks(singletonMap("preOauth",
                    new HrefType(Oauth2Controller.AUTHORIZATION_REQUEST_URI)));
                return toResponseEntity(new Response<>(200, consentsResponse, emptyResponseHeaders()),
                    mapper::toConsentsResponseTO);
            }
            throw ex;
        }
    }

    private ResponseEntity<ConsentsResponseTO> createConsent0(Map<String, String> queryParameters,
                                                              Map<String, String> headers,
                                                              ConsentsTO body) {
        Response<ConsentsResponse> response =
            accountInformationService.createConsent(queryParameters, headers, mapper.toConsents(body));
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
                                                                              Map<String, String> queryParameters,
                                                                              Map<String, String> headers) {
        return toResponseEntity(
            accountInformationService.getConsentInformation(consentId, queryParameters, headers),
            mapper::toConsentInformationResponseTO
        );
    }

    @Override
    public ResponseEntity<Void> deleteConsent(String consentId,
                                              Map<String, String> queryParameters,
                                              Map<String, String> headers) {
        Response<Void> response = accountInformationService.deleteConsent(consentId, queryParameters, headers);
        return ResponseEntity.noContent()
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .build();
    }

    @Override
    public ResponseEntity<ConsentStatusResponseTO> getConsentStatus(String consentId,
                                                                    Map<String, String> queryParameters,
                                                                    Map<String, String> headers) {
        return toResponseEntity(
            accountInformationService.getConsentStatus(consentId, queryParameters, headers),
            mapper::toConsentStatusResponseTO
        );
    }

    @Override
    public ResponseEntity<StartScaProcessResponseTO> startConsentAuthorisation(String consentId,
                                                                               Map<String, String> queryParameters,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        return toResponseEntity(
            accountInformationService.startConsentAuthorisation(consentId,
                queryParameters,
                headers,
                mapper.toUpdateAuthorisation(body)),
            mapper::toStartScaProcessResponseTO
        );
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId,
                                                                   String authorisationId,
                                                                   Map<String, String> queryParameters,
                                                                   Map<String, String> headers) {
        return toResponseEntity(
            accountInformationService.getConsentScaStatus(consentId, authorisationId, queryParameters, headers),
            mapper::toScaStatusResponseTO
        );
    }

    @Override
    public ResponseEntity<UpdateAuthorisationResponseTO> updateConsentsPsuData(String consentId,
                                                                               String authorisationId,
                                                                               Map<String, String> queryParameters,
                                                                               Map<String, String> headers,
                                                                               UpdateAuthorisationTO body) {
        return toResponseEntity(
            accountInformationService.updateConsentsPsuData(consentId,
                authorisationId,
                queryParameters,
                headers,
                mapper.toUpdateAuthorisation(body)),
            mapper::toUpdateAuthorisationResponseTO
        );
    }

    @Override
    public ResponseEntity<CardAccountListTO> getCardAccountList(Map<String, String> queryParameters,
                                                                Map<String, String> headers) throws IOException {
        return toResponseEntity(accountInformationService.getCardAccountList(queryParameters, headers),
            mapper::toCardAccountListTO);
    }

    @Override
    public ResponseEntity<CardAccountDetailsResponseTO> getCardAccountDetails(
        String accountId,
        Map<String, String> queryParameters,
        Map<String, String> headers) throws IOException {

        return toResponseEntity(accountInformationService.getCardAccountDetails(accountId, queryParameters, headers),
            mapper::toCardAccountDetailsResponseTO);
    }

    @Override
    public ResponseEntity<ReadCardAccountBalanceResponseTO> getCardAccountBalances(
        String accountId,
        Map<String, String> queryParameters,
        Map<String, String> headers) throws IOException {

        return toResponseEntity(accountInformationService.getCardAccountBalances(accountId, queryParameters, headers),
            mapper::toReadCardAccountBalanceResponseTO);
    }

    @Override
    public ResponseEntity<CardAccountsTransactionsResponseTO> getCardAccountTransactionList(
        String accountId,
        Map<String, String> queryParameters,
        Map<String, String> headers) throws IOException {

        return toResponseEntity(
            accountInformationService.getCardAccountTransactionList(accountId, queryParameters, headers),
            mapper::toCardAccountsTransactionsResponseTO);
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
    @SuppressWarnings("unchecked")
    public ResponseEntity getTransactionList(String accountId,
                                             Map<String, String> queryParameters,
                                             Map<String, String> headers) throws IOException {
        Response response = accountInformationService.getTransactions(accountId, queryParameters, headers);
        if (response.getBody() instanceof TransactionsResponse) {
            return toResponseEntity(

                (Response<TransactionsResponse>) response,
                mapper::toTransactionsResponseTO
            );
        }
        return toResponseEntity(response, Function.identity());
    }
}
