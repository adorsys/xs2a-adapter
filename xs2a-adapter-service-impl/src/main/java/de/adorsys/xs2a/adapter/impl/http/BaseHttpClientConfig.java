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

package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;

public class BaseHttpClientConfig implements HttpClientConfig {

    private final HttpLogSanitizer logSanitizer;
    private final Pkcs12KeyStore keyStore;
    private final String wiremockStandaloneUrl;

    public BaseHttpClientConfig(HttpLogSanitizer logSanitizer, Pkcs12KeyStore keyStore,
                                String wiremockStandaloneUrl) {
        this.logSanitizer = logSanitizer;
        this.keyStore = keyStore;
        this.wiremockStandaloneUrl = wiremockStandaloneUrl;
    }

    @Override
    public HttpLogSanitizer getLogSanitizer() {
        return logSanitizer;
    }

    @Override
    public Pkcs12KeyStore getKeyStore() {
        return keyStore;
    }

    public String getWiremockStandaloneUrl() {
        return wiremockStandaloneUrl;
    }
}
