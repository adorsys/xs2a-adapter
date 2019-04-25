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

package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.adapter.BaseAccountInformationService;

import java.util.Map;

public class VerlagAccountInformationService extends BaseAccountInformationService {
    private static final String BASE_URI = "https://www.sandbox-bvxs2a.de/nationalbank";

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        addApiKey(headers);
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePutHeaders(map);
        addApiKey(headers);
        return headers;

    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        addApiKey(headers);
        return headers;
    }

    @Override
    protected String getConsentBaseUri() {
        return BASE_URI + "/v1/consents";
    }

    @Override
    protected String getAccountsBaseUri() {
        return BASE_URI + "/v1/accounts";
    }

    private void addApiKey(Map<String, String> headers) {
        headers.put("X-bvpsd2-test-apikey","tUfZ5KOHRTFrikZUsmSMUabKw09UIzGE");
    }
}
