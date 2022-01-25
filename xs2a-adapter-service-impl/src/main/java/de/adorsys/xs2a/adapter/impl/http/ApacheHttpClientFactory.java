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

import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ApacheHttpClientFactory implements HttpClientFactory {

    private final HttpClientBuilder httpClientBuilder;
    private final ConcurrentMap<String, HttpClient> cache = new ConcurrentHashMap<>();
    private final HttpClientConfig clientConfig;

    public ApacheHttpClientFactory(HttpClientBuilder httpClientBuilder, HttpClientConfig clientConfig) {
        this.httpClientBuilder = httpClientBuilder;
        this.clientConfig = clientConfig;
    }

    @Override
    public HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites) {
        return cache.computeIfAbsent(adapterId, key -> createHttpClient(qwacAlias, supportedCipherSuites));
    }

    @Override
    public HttpClientConfig getHttpClientConfig() {
        return clientConfig;
    }

    private HttpClient createHttpClient(String qwacAlias, String[] supportedCipherSuites) {
        synchronized (this) {
            CloseableHttpClient httpClient;
            SSLContext sslContext = getSslContext(qwacAlias);
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(socketFactory, null, supportedCipherSuites, (HostnameVerifier) null);
            httpClientBuilder.setSSLSocketFactory(sslSocketFactory);
            httpClient = httpClientBuilder.build();
            return new ApacheHttpClient(clientConfig.getLogSanitizer(), httpClient);
        }
    }

    private SSLContext getSslContext(String qwacAlias) {
        try {
            return clientConfig.getKeyStore().getSslContext(qwacAlias);
        } catch (GeneralSecurityException e) {
            throw new Xs2aAdapterException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
