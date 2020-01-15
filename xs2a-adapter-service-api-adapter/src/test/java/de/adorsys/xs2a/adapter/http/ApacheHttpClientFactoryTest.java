package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApacheHttpClientFactoryTest {

    private ApacheHttpClientFactory factory;

    @BeforeEach
    public void setUp() throws Exception {
        Pkcs12KeyStore pkcs12KeyStore = mock(Pkcs12KeyStore.class);
        when(pkcs12KeyStore.getSslContext(any()))
            .thenReturn(SSLContext.getDefault());
        factory = new ApacheHttpClientFactory(HttpClientBuilder.create(), pkcs12KeyStore);
    }

    @Test
    public void getHttpClientCachesClientsByAdapterId() {
        HttpClient httpClient = factory.getHttpClient("test-adapter");
        HttpClient httpClient2 = factory.getHttpClient("test-adapter");

        assertThat(httpClient).isSameAs(httpClient2);

        HttpClient httpClient3 = factory.getHttpClient("another-test-adapter");

        assertThat(httpClient).isNotSameAs(httpClient3);
    }
}
