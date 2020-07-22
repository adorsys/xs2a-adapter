package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
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
        when(pkcs12KeyStore.getSslContext(any()))
            .thenReturn(SSLContext.getDefault());
        factory = new ApacheHttpClientFactory(HttpClientBuilder.create(), pkcs12KeyStore);
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
