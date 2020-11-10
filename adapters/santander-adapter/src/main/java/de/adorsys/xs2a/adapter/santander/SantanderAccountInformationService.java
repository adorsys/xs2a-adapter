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

package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenService;
import de.adorsys.xs2a.adapter.santander.model.SantanderOK200TransactionDetails;
import de.adorsys.xs2a.adapter.santander.model.SantanderTransactionResponse200Json;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.adorsys.xs2a.adapter.santander.SantanderAccessTokenService.SANTANDER_TOKEN_CONSUMER_KEY_PROPERTY;

public class SantanderAccountInformationService extends BaseAccountInformationService {
    private static final Set<String> HEADERS_TO_KEEP = new HashSet<>(Arrays.asList(
        RequestHeaders.X_REQUEST_ID,
        RequestHeaders.ACCEPT,
        RequestHeaders.CONTENT_TYPE,
        RequestHeaders.CONSENT_ID
    ));

    private final SantanderMapper santanderMapper = Mappers.getMapper(SantanderMapper.class);
    private AccessTokenService accessService;

    public SantanderAccountInformationService(Aspsp aspsp,
                                              AccessTokenService accessService,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
        this.accessService = accessService;
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

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

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           SantanderOK200TransactionDetails.class,
                                           santanderMapper::toOK200TransactionDetails);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    private Map<String, String> updateHeaders(Map<String, String> headers) {
        removeUnneededHeaders(headers);
        addBearerHeader(headers);
        addIbmClientIdHeader(headers);
        return headers;
    }

    private void removeUnneededHeaders(Map<String, String> headers) {
        headers.keySet().removeIf(header -> !HEADERS_TO_KEEP.contains(header));
    }

    private void addBearerHeader(Map<String, String> headers) {
        headers.put("Authorization", "Bearer " + accessService.retrieveToken());
    }

    private void addIbmClientIdHeader(Map<String, String> headers) {
        headers.put("x-ibm-client-id", getConsumerKey());
    }

    private String getConsumerKey() {
        return AdapterConfig.readProperty(SANTANDER_TOKEN_CONSUMER_KEY_PROPERTY, "");
    }
}
