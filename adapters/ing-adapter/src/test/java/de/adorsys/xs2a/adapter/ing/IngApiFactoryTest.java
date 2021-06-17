package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngApiFactoryTest {

    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpClientConfig httpClientConfig;

    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any(), any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
    }

    @Test
    void getAccountInformationApi() {
        IngAccountInformationApi actual = IngApiFactory.getAccountInformationApi(aspsp, httpClientFactory);

        assertThat(actual)
            .isNotNull();
    }

    @Test
    void getPaymentInitiationApi() {
        IngPaymentInitiationApi actual = IngApiFactory.getPaymentInitiationApi(aspsp, httpClientFactory);

        assertThat(actual)
            .isNotNull();
    }

    @Test
    void getOAuth2Api() {
        IngOauth2Api actual = IngApiFactory.getOAuth2Api(aspsp, httpClientFactory);

        assertThat(actual)
            .isNotNull();
    }
}
