package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractMap;
import java.util.Map;

import static com.google.common.collect.Maps.immutableEntry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerlagPaymentInitiationServiceTest {

    private static final String API_KEY = "apiKey";
    private static final String API_VALUE = "apiValue";

    private VerlagPaymentInitiationService paymentInitiationService;

    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private LinksRewriter linksRewriter;

    private final Aspsp aspsp = new Aspsp();
    private final AbstractMap.SimpleImmutableEntry<String, String> apiKeyEntry
        = new AbstractMap.SimpleImmutableEntry<>(API_KEY, API_VALUE);
    private Map<String, String> headers;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any(), any(), any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        paymentInitiationService = new VerlagPaymentInitiationService(aspsp, apiKeyEntry, httpClientFactory, null, linksRewriter);
        headers = RequestHeaders.empty().toMap();
    }

    @Test
    void populatePostHeaders() {
        Map<String, String> actualHeaders = paymentInitiationService.populatePostHeaders(headers);

        assertHeaders(actualHeaders);
    }

    private void assertHeaders(Map<String, String> actualHeaders) {
        assertThat(actualHeaders)
            .hasSize(1)
            .contains(immutableEntry(API_KEY, API_VALUE));
    }

    @Test
    void populatePutHeaders() {
        Map<String, String> actualHeaders = paymentInitiationService.populatePutHeaders(headers);

        assertHeaders(actualHeaders);
    }

    @Test
    void populateGetHeaders() {
        Map<String, String> actualHeaders = paymentInitiationService.populateGetHeaders(headers);

        assertHeaders(actualHeaders);
    }
}
