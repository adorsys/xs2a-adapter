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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class VerlagPaymentInitiationService extends BasePaymentInitiationService {

    private AbstractMap.SimpleImmutableEntry<String, String> apiKey;

    public VerlagPaymentInitiationService(Aspsp aspsp,
                                          AbstractMap.SimpleImmutableEntry<String, String> apiKey,
                                          HttpClient httpClient,
                                          List<Interceptor> interceptors,
                                          LinksRewriter linksRewriter,
                                          HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, interceptors, linksRewriter, logSanitizer);
        this.apiKey = apiKey;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        return addApiKey(map);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return addApiKey(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return addApiKey(headers);
    }

    private Map<String, String> addApiKey(Map<String, String> headers) {
        headers.put(apiKey.getKey(), apiKey.getValue());
        return headers;
    }
}
