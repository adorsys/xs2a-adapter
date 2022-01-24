/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
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
