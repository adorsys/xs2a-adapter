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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.*;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.verlag.model.VerlagOK200TransactionDetails;
import de.adorsys.xs2a.adapter.verlag.model.VerlagTransactionResponse200Json;
import org.mapstruct.factory.Mappers;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.adorsys.xs2a.adapter.api.http.ContentType.APPLICATION_JSON;

public class VerlagAccountInformationService extends BaseAccountInformationService {

    private final VerlagMapper verlagMapper = Mappers.getMapper(VerlagMapper.class);
    private AbstractMap.SimpleImmutableEntry<String, String> apiKey;

    public VerlagAccountInformationService(Aspsp aspsp,
                                           AbstractMap.SimpleImmutableEntry<String, String> apiKey,
                                           HttpClientFactory httpClientFactory,
                                           List<Interceptor> interceptors,
                                           LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId(), null, VerlagServiceProvider.SUPPORTED_CIPHER_SUITES),
            interceptors,
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        this.apiKey = apiKey;
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return super.getTransactionListAsString(accountId, modifyAcceptHeader(requestHeaders), requestParams);
    }

    // Needed to deal with the behaviour of ASPSP, that responds with 406 on any value except 'application/json'
    // for Accept header in production
    // ASPSP Sandbox supports 'text/plain' only which is also concerned
    private RequestHeaders modifyAcceptHeader(RequestHeaders requestHeaders) {
        if (isTextPlain(requestHeaders)) {
            return requestHeaders;
        }

        return setAcceptJson(requestHeaders);
    }

    private boolean isTextPlain(RequestHeaders requestHeaders) {
        Optional<String> acceptHeaderOptional = requestHeaders.get(RequestHeaders.ACCEPT);
        return acceptHeaderOptional.isPresent() && ContentType.TEXT_PLAIN.equalsIgnoreCase(acceptHeaderOptional.get());
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        VerlagTransactionResponse200Json.class,
                                        verlagMapper::toTransactionsResponse200Json);
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
                                           VerlagOK200TransactionDetails.class,
                                           verlagMapper::toOK200TransactionDetails);
    }

    private RequestHeaders setAcceptJson(RequestHeaders requestHeaders) {
        Map<String, String> headersMap = requestHeaders.toMap();
        headersMap.put(RequestHeaders.ACCEPT, APPLICATION_JSON);
        return RequestHeaders.fromMap(headersMap);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return addApiKey(headers);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return addApiKey(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return addApiKey(headers);
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return addApiKey(headers);
    }

    private Map<String, String> addApiKey(Map<String, String> headers) {
        headers.put(apiKey.getKey(), apiKey.getValue());
        return headers;
    }
}
