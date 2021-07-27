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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.PaymentInitiationJson;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.http.StringUri;

import java.util.EnumMap;
import java.util.Map;

public abstract class AbstractService {
    private static final EnumMap<PaymentService, Class<?>> PAYMENT_INITIATION_BODY_CLASSES =
        new EnumMap<>(PaymentService.class);

    protected static final String AUTHORISATIONS = "authorisations";
    protected static final String STATUS = "status";
    protected static final String ACCEPT_HEADER = "Accept";
    protected final JsonMapper jsonMapper = new JacksonObjectMapper();
    protected final HttpClient httpClient;

    static {
        PAYMENT_INITIATION_BODY_CLASSES.put(PaymentService.PAYMENTS, PaymentInitiationJson.class);
        PAYMENT_INITIATION_BODY_CLASSES.put(PaymentService.PERIODIC_PAYMENTS, PeriodicPaymentInitiationJson.class);
    }

    protected AbstractService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * In default implementation, adds a PSU-ID header.
     *
     * @param headers request headers
     * @return list of headers with added an empty PSU-ID header
     */
    protected Map<String, String> resolvePsuIdHeader(Map<String, String> headers) {
        if (!headers.containsKey(RequestHeaders.PSU_ID)) {
            headers.put(RequestHeaders.PSU_ID, "");
        }

        return headers;
    }

    protected Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        return headers;
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

    protected Class<?> getPaymentInitiationBodyClass(PaymentService paymentService) {
        Class<?> paymentInitiationBodyClass = PAYMENT_INITIATION_BODY_CLASSES.get(paymentService);
        if (paymentInitiationBodyClass == null) {
            throw new IllegalArgumentException("Unsupported payment service: " + paymentService);
        }
        return paymentInitiationBodyClass;
    }
}
