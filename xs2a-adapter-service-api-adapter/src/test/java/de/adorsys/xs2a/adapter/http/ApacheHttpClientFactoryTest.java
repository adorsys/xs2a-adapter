package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ApacheHttpClientFactoryTest {

    private ApacheHttpClientFactory factory;

    @Before
    public void setUp() {
        Pkcs12KeyStore mock = mock(Pkcs12KeyStore.class);
        factory = new ApacheHttpClientFactory(HttpClientBuilder.create(), mock);
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
