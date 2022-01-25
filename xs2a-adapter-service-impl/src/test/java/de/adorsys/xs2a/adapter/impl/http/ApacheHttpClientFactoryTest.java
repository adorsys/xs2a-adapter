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
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApacheHttpClientFactoryTest {

    private ApacheHttpClientFactory factory;
    private Pkcs12KeyStore pkcs12KeyStore;

    @BeforeEach
    public void setUp() throws Exception {
        pkcs12KeyStore = mock(Pkcs12KeyStore.class);
        HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);

        when(pkcs12KeyStore.getSslContext(any()))
            .thenReturn(SSLContext.getDefault());
        when(httpClientConfig.getKeyStore()).thenReturn(pkcs12KeyStore);
        factory = new ApacheHttpClientFactory(HttpClientBuilder.create(), httpClientConfig);
    }

    @Test
    void getHttpClientCachesClientsByAdapterId() {
        HttpClient httpClient = factory.getHttpClient("test-adapter");
        HttpClient httpClient2 = factory.getHttpClient("test-adapter");

        assertThat(httpClient).isSameAs(httpClient2);

        HttpClient httpClient3 = factory.getHttpClient("another-test-adapter");

        assertThat(httpClient).isNotSameAs(httpClient3);
    }

    @Test
    void getHttpClient_throwsException() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnrecoverableEntryException, IOException {
        when(pkcs12KeyStore.getSslContext(any())).thenThrow(new IOException());

        assertThrows(UncheckedIOException.class, () -> factory.getHttpClient("test-adapter"));
    }
}
