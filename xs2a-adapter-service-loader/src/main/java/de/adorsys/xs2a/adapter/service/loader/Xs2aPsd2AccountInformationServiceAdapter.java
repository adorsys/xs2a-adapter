package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.loader.mapper.Xs2aPsd2Mapper;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.Map;

class Xs2aPsd2AccountInformationServiceAdapter implements Psd2AccountInformationService {

    private final AccountInformationService service;
    private final Xs2aPsd2Mapper mapper = Mappers.getMapper(Xs2aPsd2Mapper.class);

    Xs2aPsd2AccountInformationServiceAdapter(AccountInformationService service) {
        this.service = service;
    }

    @Override
    public Response<AccountList> getAccounts(Map<String, String> queryParameters,
                                             Map<String, String> headers) throws IOException {

       return service.getAccountList(RequestHeaders.fromMap(headers), RequestParams.fromMap(queryParameters))
           .map(mapper::toAccountList);
    }

    @Override
    public Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                            Map<String, String> queryParameters,
                                                            Map<String, String> headers) throws IOException {
        return service.getBalances(accountId, RequestHeaders.fromMap(headers))
            .map(mapper::toReadAccountBalanceResponse);
    }

    @Override
    public Response<?> getTransactions(String accountId,
                                       Map<String, String> queryParameters,
                                       Map<String, String> headers) throws IOException {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(queryParameters);
        if (requestHeaders.isAcceptJson()) {
            return service.getTransactionList(accountId, requestHeaders, requestParams)
                .map(mapper::toTransactionsResponse);
        } else {
            return service.getTransactionListAsString(accountId, requestHeaders, requestParams);
        }
    }

    @Override
    public Response<ConsentsResponse> createConsent(Map<String, String> headers, Consents consents) {
        return service.createConsent(RequestHeaders.fromMap(headers), mapper.map(consents))
            .map(mapper::toConsentsResponse);
    }

    @Override
    public Response<ConsentInformationResponse> getConsentInformation(String consentId, Map<String, String> headers) {
        return service.getConsentInformation(consentId, RequestHeaders.fromMap(headers))
            .map(mapper::toConsentInformationResponse);
    }

    @Override
    public Response<Void> deleteConsent(String consentId, Map<String, String> headers) {
        return service.deleteConsent(consentId, RequestHeaders.fromMap(headers));
    }

    @Override
    public Response<ConsentStatusResponse> getConsentStatus(String consentId, Map<String, String> headers) {
        return service.getConsentStatus(consentId, RequestHeaders.fromMap(headers))
            .map(mapper::toConsentStatusResponse);
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           Map<String, String> headers) {
        return service.getConsentScaStatus(consentId, authorisationId, RequestHeaders.fromMap(headers))
            .map(mapper::toScaStatusResponse);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthorisation) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        if (updateAuthorisation.getPsuData() == null &&
                updateAuthorisation.getAuthenticationMethodId() == null &&
                updateAuthorisation.getScaAuthenticationData() == null) {
            return service.startConsentAuthorisation(consentId, requestHeaders)
                .map(mapper::toStartScaprocessResponse);
        }
        return service.startConsentAuthorisation(consentId, requestHeaders, mapper.map(updateAuthorisation))
            .map(mapper::toStartScaprocessResponse);
    }

    @Override
    public Response<UpdateAuthorisationResponse> updateConsentsPsuData(String consentId,
                                                                       String authorisationId,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthorisation) {

        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        if (updateAuthorisation.getAuthenticationMethodId() != null) {
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod =
                mapper.toSelectPsuAuthenticationMethod(updateAuthorisation);
            return service.updateConsentsPsuData(consentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod)
                .map(mapper::toUpdateAuthorisationResponse);
        }
        if (updateAuthorisation.getPsuData() != null) {
            UpdatePsuAuthentication updatePsuAuthentication =
                mapper.toUpdatePsuAuthentication(updateAuthorisation);
            return service.updateConsentsPsuData(consentId, authorisationId, requestHeaders, updatePsuAuthentication)
                .map(mapper::toUpdateAuthorisationResponse);
        }
        if (updateAuthorisation.getScaAuthenticationData() != null) {
            TransactionAuthorisation transactionAuthorisation =
                mapper.toTransactionAuthorisation(updateAuthorisation);
            return service.updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation)
                .map(mapper::toUpdateAuthorisationResponse);
        }

        throw new BadRequestException("Request body doesn't match any of the supported schemas");
    }
}
