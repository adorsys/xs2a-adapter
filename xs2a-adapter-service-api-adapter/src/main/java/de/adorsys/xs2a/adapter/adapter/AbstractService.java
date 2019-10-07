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

package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.JsonMapper;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.model.SinglePaymentInitiationBody;

import java.util.Map;

public abstract class AbstractService {
    protected static final String AUTHORISATIONS = "authorisations";
    protected static final String STATUS = "status";
    protected static final String ACCEPT_HEADER = "Accept";
    protected final JsonMapper jsonMapper = new JsonMapper();
    protected final HttpClient httpClient;

    public AbstractService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected Map<String, String> addConsentIdHeader(Map<String, String> headers) {
        return headers;
    }

    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return headers;
    }

    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return headers;
    }

    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return headers;
    }

    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return headers;
    }

    protected static String buildUri(String uri, RequestParams requestParams) {
        if (requestParams == null) {
            return uri;
        }

        Map<String, String> requestParamsMap = requestParams.toMap();

        return StringUri.withQuery(uri, requestParamsMap);
    }

    protected Class<?> getSinglePaymentInitiationBodyClass() {
        return SinglePaymentInitiationBody.class;
    }
}
