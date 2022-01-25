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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestSigningInterceptor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AdorsysSigningHeadersInterceptor implements Interceptor {

    private final RequestSigningInterceptor signingInterceptor;
    private final boolean requestSigningEnabled =
        Boolean.parseBoolean(AdapterConfig.readProperty("adorsys.request_signing.enabled", "false"));

    public AdorsysSigningHeadersInterceptor(Pkcs12KeyStore keyStore) {
        this.signingInterceptor = new RequestSigningInterceptor(keyStore);
    }

    @Override
    public Request.Builder preHandle(Request.Builder requestBuilder) {
        if (requestSigningEnabled) {
            requestBuilder.header(RequestHeaders.DATE,
                DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
            signingInterceptor.preHandle(requestBuilder);
            String certificate = requestBuilder.headers().get(RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
            requestBuilder.header(RequestHeaders.TPP_SIGNATURE_CERTIFICATE,
                "-----BEGIN CERTIFICATE-----" + certificate + "-----END CERTIFICATE-----");
            return requestBuilder;
        }
        return requestBuilder;
    }
}
