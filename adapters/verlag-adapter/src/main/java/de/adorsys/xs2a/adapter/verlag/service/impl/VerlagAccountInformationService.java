/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.verlag.service.impl;

import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request.Builder.Interceptor;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class VerlagAccountInformationService extends BaseAccountInformationService {
    private static final String ACCEPT_ALL = "*/*";
    private static final String ACCEPT_XML = "application/xml";

    private AbstractMap.SimpleImmutableEntry<String, String> apiKey;

    public VerlagAccountInformationService(Aspsp aspsp,
                                           AbstractMap.SimpleImmutableEntry<String, String> apiKey,
                                           HttpClient httpClient,
                                           Interceptor interceptor,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, interceptor, linksRewriter);
        this.apiKey = apiKey;
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return super.getTransactionListAsString(accountId, modifyAcceptHeader(requestHeaders), requestParams);
    }

    // Needed to deal with the behaviour of ASPSP, that responds with 406
    // on non existing, empty, equal to */* or list formatted Accept header
    private RequestHeaders modifyAcceptHeader(RequestHeaders requestHeaders) {
        Optional<String> acceptHeaderOptional = requestHeaders.get(RequestHeaders.ACCEPT);

        if (!acceptHeaderOptional.isPresent()
                || acceptHeaderOptional.get().isEmpty()
                || acceptHeaderOptional.get().equals(ACCEPT_ALL)) {
            requestHeaders = modifyAcceptHeader(requestHeaders, ACCEPT_XML);
        } else if (acceptHeaderIsAListOfValues(acceptHeaderOptional.get())) {
            String[] acceptHeaderValues = acceptHeaderOptional.get().split(",");

            if (containsXml(acceptHeaderValues)) {
                requestHeaders = modifyAcceptHeader(requestHeaders, ACCEPT_XML);
            } else {
                requestHeaders = modifyAcceptHeader(requestHeaders, acceptHeaderValues[0]);
            }
        }

        return requestHeaders;
    }

    private boolean acceptHeaderIsAListOfValues(String acceptHeader) {
        return acceptHeader.contains(",");
    }

    private boolean containsXml(String[] acceptHeaderValues) {
        return Stream.of(acceptHeaderValues)
                   .anyMatch(accept -> accept.contains(ACCEPT_XML));
    }

    private RequestHeaders modifyAcceptHeader(RequestHeaders requestHeaders, String acceptHeader) {
        Map<String, String> headersMap = requestHeaders.toMap();
        headersMap.put(RequestHeaders.ACCEPT, acceptHeader);
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
