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

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

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

    private AccessTokenService accessService;

    public SantanderAccountInformationService(Aspsp aspsp,
                                              AccessTokenService accessService,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
        this.accessService = accessService;
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
