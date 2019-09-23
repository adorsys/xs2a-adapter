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

package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;

import java.util.Map;

import static de.adorsys.xs2a.adapter.service.impl.SantanderAccessTokenService.SANTANDER_TOKEN_CONSUMER_KEY_PROPERTY;

public class SantanderAccountInformationService extends BaseAccountInformationService {
    private AccessTokenService accessService;

    public SantanderAccountInformationService(String baseUri, AccessTokenService accessService) {
        super(baseUri);
        this.accessService = accessService;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return populateHeaders(headers);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return populateHeaders(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return populateHeaders(headers);
    }

    private Map<String, String> populateHeaders(Map<String, String> headers) {
        addBearerHeader(headers);
        addIbmClientIdHeader(headers);
        return headers;
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
