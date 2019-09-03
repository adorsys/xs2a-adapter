package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.loader.mapper.Xs2aPsd2Mapper;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.Consents;
import de.adorsys.xs2a.adapter.service.psd2.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

class Xs2aPsd2AccountInformationServiceAdapter implements Psd2AccountInformationService {

    private final AccountInformationService service;
    private final Xs2aPsd2Mapper mapper = Mappers.getMapper(Xs2aPsd2Mapper.class);

    Xs2aPsd2AccountInformationServiceAdapter(AccountInformationService service) {
        this.service = service;
    }

    @Override
    public AccountList getAccounts(Map<String, String> queryParameters,
                                   Map<String, String> headers) throws IOException {
        return mapper.map(service.getAccountList(RequestHeaders.fromMap(headers), RequestParams.fromMap(queryParameters)).getBody());
    }

    @Override
    public ReadAccountBalanceResponse getBalances(String accountId,
                                                  Map<String, String> queryParameters,
                                                  Map<String, String> headers) throws IOException {
        return mapper.map(service.getBalances(accountId, RequestHeaders.fromMap(headers)).getBody());
    }

    @Override
    public Object getTransactions(String accountId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(queryParameters);
        if (requestHeaders.isAcceptJson()) {
            return mapper.map(service.getTransactionList(accountId, requestHeaders, requestParams).getBody());
        } else {
            return service.getTransactionListAsString(accountId, requestHeaders, requestParams).getBody();
        }
    }

    @Override
    public ConsentsResponse createConsent(Map<String, String> headers, Consents consents) {
        return mapper.map(service.createConsent(RequestHeaders.fromMap(headers), mapper.map(consents)).getBody());
    }

    @Override
    public ConsentInformationResponse getConsentInformation(String consentId, Map<String, String> headers) {
        return mapper.map(service.getConsentInformation(consentId, RequestHeaders.fromMap(headers)).getBody());
    }

    @Override
    public void deleteConsent(String consentId, Map<String, String> headers) {
        service.deleteConsent(consentId, RequestHeaders.fromMap(headers));
    }

    @Override
    public ConsentStatusResponse getConsentStatus(String consentId, Map<String, String> headers) {
        return mapper.map(service.getConsentStatus(consentId, RequestHeaders.fromMap(headers)).getBody());
    }

    @Override
    public ScaStatusResponse getConsentScaStatus(String consentId,
                                                 String authorisationId,
                                                 Map<String, String> headers) {
        return mapper.map(service.getConsentScaStatus(consentId, authorisationId, RequestHeaders.fromMap(headers)).getBody());
    }

    @Override
    public StartScaprocessResponse startConsentAuthorisation(String consentId,
                                                             Map<String, String> headers,
                                                             UpdateAuthorisation updateAuthorisation) {
        StartScaProcessResponse res;
        if (updateAuthorisation.getPsuData() == null &&
                updateAuthorisation.getAuthenticationMethodId() == null &&
                updateAuthorisation.getScaAuthenticationData() == null) {
            res = service.startConsentAuthorisation(consentId, RequestHeaders.fromMap(headers))
                .getBody();
        } else {
            res = service.startConsentAuthorisation(consentId, RequestHeaders.fromMap(headers), mapper.map(updateAuthorisation))
                .getBody();
        }

        return mapper.map(res);
    }

    @Override
    public UpdateAuthorisationResponse updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             Map<String, String> headers,
                                                             UpdateAuthorisation updateAuthorisation) {

        if (updateAuthorisation.getAuthenticationMethodId() != null) {
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod = new SelectPsuAuthenticationMethod();
            mapper.map(updateAuthorisation, selectPsuAuthenticationMethod);
            return mapper.map(
                service.updateConsentsPsuData(consentId, authorisationId, RequestHeaders.fromMap(headers), selectPsuAuthenticationMethod)
                    .getBody()
            );
        }
        if (updateAuthorisation.getPsuData() != null) {
            UpdatePsuAuthentication updatePsuAuthentication = new UpdatePsuAuthentication();
            mapper.map(updateAuthorisation, updatePsuAuthentication);
            return mapper.map(
                service.updateConsentsPsuData(consentId, authorisationId, RequestHeaders.fromMap(headers), updatePsuAuthentication)
                    .getBody()
            );
        }
        if (updateAuthorisation.getScaAuthenticationData() != null) {
            TransactionAuthorisation transactionAuthorisation = new TransactionAuthorisation();
            mapper.map(updateAuthorisation, transactionAuthorisation);
            return mapper.toUpdateAuthorisationResponse(
                service.updateConsentsPsuData(consentId, authorisationId, RequestHeaders.fromMap(headers), transactionAuthorisation)
                    .getBody()
            );
        }

        throw new BadRequestException("Request body doesn't match any of the supported schemas");
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers,
                                          String state,
                                          URI redirectUri) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, String authorizationCode) throws IOException {
        throw new UnsupportedOperationException();
    }
}
