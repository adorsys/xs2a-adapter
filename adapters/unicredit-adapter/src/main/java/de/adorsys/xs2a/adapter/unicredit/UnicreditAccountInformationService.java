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

package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditOK200TransactionDetails;
import de.adorsys.xs2a.adapter.unicredit.model.UnicreditTransactionResponse200Json;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

public class UnicreditAccountInformationService extends BaseAccountInformationService {

    private final UnicreditMapper unicreditMapper = Mappers.getMapper(UnicreditMapper.class);

    public UnicreditAccountInformationService(Aspsp aspsp,
                                              HttpClientFactory httpClientFactory,
                                              LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        UnicreditTransactionResponse200Json.class,
                                        unicreditMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           UnicreditOK200TransactionDetails.class,
                                           unicreditMapper::toOK200TransactionDetails);
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        return super.getAccountList(requestHeaders, RequestParams.builder().build());
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        map.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return map;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        return UnicreditHeaders.addPsuIdTypeHeader(headers);
    }

    @Override
    public List<ValidationError> validateCreateConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }



    @Override
    public List<ValidationError> validateStartConsentAuthorisation(String consentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }

    @Override
    public List<ValidationError> validateStartConsentAuthorisation(String consentId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication updatePsuAuthentication) {
        return UnicreditValidators.requireTppRedirectUri(requestHeaders);
    }
}
