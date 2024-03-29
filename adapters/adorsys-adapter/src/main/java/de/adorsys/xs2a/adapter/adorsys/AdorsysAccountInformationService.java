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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.adorsys.model.AdorsysOK200TransactionDetails;
import de.adorsys.xs2a.adapter.adorsys.model.AdorsysTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;

public class AdorsysAccountInformationService extends BaseAccountInformationService {
    private static final String ACCEPT_ALL = "*/*";
    private static final String ACCEPT_JSON = "application/json";

    private final AdorsysMapper mapper = Mappers.getMapper(AdorsysMapper.class);
    private final ResponseHandlers handlers;

    public AdorsysAccountInformationService(Aspsp aspsp,
                                            HttpClientFactory httpClientFactory,
                                            List<Interceptor> interceptors,
                                            LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            interceptors,
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        this.handlers = new ResponseHandlers(httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return createConsent(requestHeaders,
                             requestParams,
                             body,
                             identity(),
                             handlers.consentCreationResponseHandler(getIdpUri(), ConsentsResponse201.class));
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        AdorsysTransactionsResponse200Json.class,
                                        mapper::toTransactionsResponse200Json);
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
                                           AdorsysOK200TransactionDetails.class,
                                           mapper::toOK200TransactionDetails);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return super.populatePostHeaders(modifyAcceptHeader(headers));
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return super.populatePutHeaders(modifyAcceptHeader(headers));
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return super.populateGetHeaders(modifyAcceptHeader(headers));
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return super.populateDeleteHeaders(modifyAcceptHeader(headers));
    }

    private Map<String, String> modifyAcceptHeader(Map<String, String> headers) {
        String acceptValue = headers.get(RequestHeaders.ACCEPT);

        if (StringUtils.isBlank(acceptValue) || acceptValue.equals(ACCEPT_ALL)) {
            headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);
        }

        return headers;
    }
}
