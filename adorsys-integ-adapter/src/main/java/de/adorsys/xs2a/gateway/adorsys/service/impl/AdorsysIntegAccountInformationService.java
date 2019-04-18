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

package de.adorsys.xs2a.gateway.adorsys.service.impl;

import de.adorsys.xs2a.gateway.adapter.BaseAccountInformationService;

import java.util.Map;

public class AdorsysIntegAccountInformationService extends BaseAccountInformationService {
    private static final String CONSENT_URI = "https://dev-xs2a.cloud.adorsys.de/v1/consents";
    private static final String ACCOUNTS_URI = "https://dev-xs2a.cloud.adorsys.de/v1/accounts";

    @Override
    protected String getConsentBaseUri() {
        return CONSENT_URI;
    }

    @Override
    protected String getAccountsBaseUri() {
        return ACCOUNTS_URI;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(ACCEPT_HEADER, APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);
        return headers;
    }
}