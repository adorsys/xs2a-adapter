package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.google.common.collect.Maps.immutableEntry;
import static de.adorsys.xs2a.adapter.api.RequestHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SantanderPaymentInitiationServiceTest {

    private static final String TOKEN = "token";
    private static final String BEARER_TOKEN = "Bearer " + TOKEN;
    private static final String X_IBM_CLIENT_ID_HEADER = "x-ibm-client-id";

    private SantanderPaymentInitiationService paymentInitiationService;

    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private AccessTokenService accessTokenService;
    private final Aspsp aspsp = new Aspsp();
    private Map<String, String> headers;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(accessTokenService.retrieveToken()).thenReturn(TOKEN);

        paymentInitiationService = new SantanderPaymentInitiationService(aspsp, httpClientFactory, null, accessTokenService);
        headers = RequestHeaders.empty().toMap();
    }

    @Test
    void populatePostHeaders() {
        Map<String, String> actualHeaders = paymentInitiationService.populatePostHeaders(headers);

        assertHeaders(actualHeaders);
    }

    private void assertHeaders(Map<String, String> actualHeaders) {
        assertThat(actualHeaders)
            .hasSize(2)
            .contains(immutableEntry(X_IBM_CLIENT_ID_HEADER, ""),
                immutableEntry(AUTHORIZATION, BEARER_TOKEN));
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

    @Test
    void populateDeleteHeaders() {
        Map<String, String> actualHeaders = paymentInitiationService.populateDeleteHeaders(headers);

        assertHeaders(actualHeaders);
    }
}
