package de.adorsys.xs2a.adapter.sparda;

import com.google.common.collect.Maps;
import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentInitiationJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpardaPaymentInitiationServiceTest {

    private static final String PSU_ID = "psuId";

    private PaymentInitiationService paymentInitiationService;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private HttpClient httpClient;
    @Mock
    private LinksRewriter linksRewriter;
    @Mock
    private SpardaJwtService spardaJwtService;

    @Captor
    private ArgumentCaptor<Map<String, String>> headersCaptor;

    private final Aspsp aspsp = getAspsp();

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        paymentInitiationService = new SpardaPaymentInitiationService(aspsp, httpClientFactory, linksRewriter, spardaJwtService);
    }

    @Test
    void initiatePayment() {
        Request.Builder requestBuilder = mock(Request.Builder.class);
        when(httpClient.post(any())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), anyList()))
            .thenReturn(new Response<>(-1, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        when(spardaJwtService.getPsuId(any())).thenReturn(PSU_ID);

        paymentInitiationService.initiatePayment(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            getAuthRequestHeaders(),
            RequestParams.empty(),
            new PaymentInitiationJson());

        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        Map<String, String> actualHeaders = headersCaptor.getValue();

        assertThat(actualHeaders)
            .hasSize(2)
            .contains(Maps.immutableEntry(RequestHeaders.PSU_ID, PSU_ID));
    }

    private RequestHeaders getAuthRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.AUTHORIZATION, "Bearer token");
        return RequestHeaders.fromMap(headers);
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl("https://foo.boo");
        return aspsp;
    }
}
