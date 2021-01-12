package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixOK200TransactionDetails;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.mapstruct.factory.Mappers;

import static java.util.function.Function.identity;

public class CrealogixAccountInformationService extends BaseAccountInformationService {

    private final CrealogixMapper crealogixMapper = Mappers.getMapper(CrealogixMapper.class);
    private final CrealogixRequestResponseHandlers requestResponseHandlers;

    public CrealogixAccountInformationService(Aspsp aspsp,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter,
                                              HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, linksRewriter, logSanitizer);
        this.requestResponseHandlers = new CrealogixRequestResponseHandlers(logSanitizer);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            requestResponseHandlers.crealogixResponseHandler(ConsentsResponse201.class));
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getConsentInformation(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.deleteConsent(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getConsentStatus(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.startConsentAuthorisation(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.startConsentAuthorisation(consentId, requestHeaders, requestParams, updatePsuAuthentication);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.updateConsentsPsuData(consentId, authorisationId, requestHeaders, requestParams, updatePsuAuthentication);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.updateConsentsPsuData(consentId, authorisationId, requestHeaders, requestParams, selectPsuAuthenticationMethod);
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation transactionAuthorisation) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.updateConsentsPsuData(consentId, authorisationId, requestHeaders, requestParams, transactionAuthorisation);
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                                RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getAccountList(requestHeaders, requestParams);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        CrealogixTransactionResponse200Json.class,
                                        crealogixMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           CrealogixOK200TransactionDetails.class,
                                           crealogixMapper::toOK200TransactionDetails);
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getTransactionListAsString(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getConsentScaStatus(consentId, authorisationId, requestHeaders, requestParams);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getBalances(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders,
                                                        RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getCardAccountList(requestHeaders, requestParams);
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getCardAccountDetails(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getCardAccountBalances(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        requestResponseHandlers.crealogixRequestHandler(requestHeaders);

        return super.getCardAccountTransactionList(accountId, requestHeaders, requestParams);
    }
}
