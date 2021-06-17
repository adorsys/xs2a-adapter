package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
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
                                              HttpClientFactory httpClientFactory,
                                              LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        this.requestResponseHandlers = new CrealogixRequestResponseHandlers(httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return super.createConsent(requestHandler(requestHeaders),
            requestParams,
            body,
            identity(),
            requestResponseHandlers.crealogixResponseHandler(ConsentsResponse201.class));
    }

    private RequestHeaders requestHandler(RequestHeaders requestHeaders) {
        return requestResponseHandlers.crealogixRequestHandler(requestHeaders);
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return super.getConsentInformation(consentId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        return super.deleteConsent(consentId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return super.getConsentStatus(consentId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return super.startConsentAuthorisation(consentId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return super.startConsentAuthorisation(consentId,
            requestHandler(requestHeaders),
            requestParams,
            updatePsuAuthentication);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams,
            updatePsuAuthentication);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams,
            selectPsuAuthenticationMethod);
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation transactionAuthorisation) {
        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHandler(requestHeaders),
            requestParams,
            transactionAuthorisation);
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                                RequestParams requestParams) {
        return super.getAccountList(requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<OK200AccountDetails> getAccountDetails(String accountId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return super.getAccountDetails(accountId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return super.getTransactionList(accountId,
            requestHandler(requestHeaders),
            requestParams,
            CrealogixTransactionResponse200Json.class,
            crealogixMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return super.getTransactionDetails(accountId,
            transactionId,
            requestHandler(requestHeaders),
            requestParams,
            CrealogixOK200TransactionDetails.class,
            crealogixMapper::toOK200TransactionDetails);
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        return super.getTransactionListAsString(accountId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return super.getConsentScaStatus(consentId, authorisationId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return super.getBalances(accountId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders,
                                                        RequestParams requestParams) {
        return super.getCardAccountList(requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return super.getCardAccountDetails(accountId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        return super.getCardAccountBalances(accountId, requestHandler(requestHeaders), requestParams);
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        return super.getCardAccountTransactionList(accountId, requestHandler(requestHeaders), requestParams);
    }
}
