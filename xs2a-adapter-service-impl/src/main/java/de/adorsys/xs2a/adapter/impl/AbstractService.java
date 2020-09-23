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
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitiationJson;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockStubDifferenceDetectingInterceptor;

import java.util.*;

import static de.adorsys.xs2a.adapter.api.config.AdapterConfig.WIREMOCK_VALIDATION_ENABLED;

public abstract class AbstractService {
    private static final EnumMap<PaymentService, Class<?>> PAYMENT_INITIATION_BODY_CLASSES =
        new EnumMap<>(PaymentService.class);
    private final boolean wiremockInterceptorEnabled =
        Boolean.parseBoolean(AdapterConfig.readProperty(WIREMOCK_VALIDATION_ENABLED, "false"));

    protected static final String AUTHORISATIONS = "authorisations";
    protected static final String STATUS = "status";
    protected static final String ACCEPT_HEADER = "Accept";
    protected final JsonMapper jsonMapper = new JacksonObjectMapper();
    protected final HttpClient httpClient;

    static {
        PAYMENT_INITIATION_BODY_CLASSES.put(PaymentService.PAYMENTS, PaymentInitiationJson.class);
        PAYMENT_INITIATION_BODY_CLASSES.put(PaymentService.PERIODIC_PAYMENTS, PeriodicPaymentInitiationJson.class);
    }

    public AbstractService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected Map<String, String> addPsuIdHeader(Map<String, String> headers) {
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

    protected List<Interceptor> populateInterceptors(List<Interceptor> interceptors, Aspsp aspsp) {
        if (wiremockInterceptorEnabled && WiremockStubDifferenceDetectingInterceptor.isWiremockSupported(aspsp.getName())) {
            List<Interceptor> list = new ArrayList<>(interceptors);
            list.add(new WiremockStubDifferenceDetectingInterceptor(aspsp));
            return Collections.unmodifiableList(list);
        }
        return interceptors;
    }
}
