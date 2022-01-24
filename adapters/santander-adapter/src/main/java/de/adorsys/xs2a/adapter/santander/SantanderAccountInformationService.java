/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.santander.model.SantanderTransactionResponse200Json;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class SantanderAccountInformationService extends BaseAccountInformationService {

    private final SantanderMapper santanderMapper = Mappers.getMapper(SantanderMapper.class);
    private final SantanderOauth2Service oauth2Service;
    private final SantanderRequestHandler requestHandler = new SantanderRequestHandler();

    public SantanderAccountInformationService(Aspsp aspsp,
                                              SantanderOauth2Service oauth2Service,
                                              HttpClientFactory httpClientFactory,
                                              LinksRewriter linksRewriter) {
        super(aspsp,
            SantanderHttpClientFactory.getHttpClient(aspsp.getAdapterId(), httpClientFactory),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        this.oauth2Service = oauth2Service;
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        Map<String, String> updatedHeaders = addBearerHeader(requestHeaders.toMap());

        return super.createConsent(RequestHeaders.fromMap(updatedHeaders), requestParams, body);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation transactionAuthorisation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getConsentInformation(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getConsentStatus(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.deleteConsent(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                                RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getAccountList(requestHeaders, requestParams);
    }

    @Override
    public Response<OK200AccountDetails> readAccountDetails(String accountId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.readAccountDetails(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getBalances(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        requestHandler.validateRequest(requestHeaders);

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        SantanderTransactionResponse200Json.class,
                                        santanderMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {

        throw new UnsupportedOperationException();
    }

    @Override
    public Response<Authorisations> getConsentAuthorisation(String consentId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders,
                                                        RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    private Map<String, String> addBearerHeader(Map<String, String> headers) {
        headers.put("Authorization", "Bearer " + oauth2Service.getClientCredentialsAccessToken());
        return headers;
    }

    @Override
    protected Map<String, String> resolvePsuIdHeader(Map<String, String> headers) {
        headers.remove(RequestHeaders.PSU_ID); // will actually fail if PSU-ID is provided
        return headers;
    }
}
