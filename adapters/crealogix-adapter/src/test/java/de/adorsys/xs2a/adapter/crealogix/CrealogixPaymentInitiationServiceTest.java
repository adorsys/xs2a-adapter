package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.PaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrealogixPaymentInitiationServiceTest {

    private final HttpClient httpClient = mock(HttpClient.class);
    private final Aspsp aspsp = getAspsp();
    private final PaymentInitiationService service
        = new CrealogixPaymentInitiationService(aspsp, httpClient, null);

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

    private CrealogixPaymentInitiationWithStatusResponse getResponseBody() {
        return new CrealogixPaymentInitiationWithStatusResponse();
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }
}
