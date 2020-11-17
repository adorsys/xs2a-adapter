package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CrealogixPaymentInitiationServiceTest {

    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_VALUE = "Bearer foo";
    private final HttpClient httpClient = mock(HttpClient.class);
    private final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    private final Aspsp aspsp = getAspsp();
    private PaymentInitiationService service;

    @BeforeEach
    void setUp() {
        service = new CrealogixPaymentInitiationService(aspsp, httpClient, linksRewriter, null);
    }

    @Test
    void getSinglePaymentInformation() {
        when(httpClient.get(any())).thenReturn(new RequestBuilderImpl(httpClient, "GET", ""));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, getResponseBody(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse = service.getSinglePaymentInformation(PaymentProduct.SEPA_CREDIT_TRANSFERS,
            "paymentId",
            RequestHeaders.empty(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(PaymentInitiationWithStatusResponse.class);
    }

    @Test
    void initiatePayment() {
        when(httpClient.post(anyString())).thenReturn(new RequestBuilderImpl(httpClient, "POST", ""));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(
                -1,
                new PaymentInitationRequestResponse201(),
                ResponseHeaders.emptyResponseHeaders()));

        Response<PaymentInitationRequestResponse201> actualResponse
            = service.initiatePayment(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty(),
                new PaymentInitiationJson()
            );

        verify(httpClient, times(1)).post(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(PaymentInitationRequestResponse201.class);
    }

    private CrealogixPaymentInitiationWithStatusResponse getResponseBody() {
        return new CrealogixPaymentInitiationWithStatusResponse();
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }
}
