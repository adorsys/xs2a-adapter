package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaExecutionRule;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient.ResponseHandler;
import de.adorsys.xs2a.adapter.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class FiduciaPaymentInitiationServiceTest {
    private FiduciaPaymentInitiationService fiduciaPaymentInitiationService;
    private HttpClient httpClient;

    // todo challenge data

    @BeforeEach
    void setUp() {
        httpClient = Mockito.spy(AbstractHttpClient.class);
        fiduciaPaymentInitiationService =
            new FiduciaPaymentInitiationService(new Aspsp(), httpClient, null, new IdentityLinksRewriter());
    }

    @Test
    void initiatePeriodicSctPaymentUsesFiduciaExecutionRule() {
        PeriodicPaymentInitiationJson body = new PeriodicPaymentInitiationJson();
        body.setExecutionRule(ExecutionRule.PRECEDING);
        Mockito.doReturn(new Response<>(201,
            new PaymentInitationRequestResponse201(),
            null))
            .when(httpClient).send(Mockito.any(), Mockito.any());

        fiduciaPaymentInitiationService.initiatePayment(PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            RequestHeaders.empty(),
            null,
            body);

        FiduciaPeriodicPaymentInitiationJson actualBody = new FiduciaPeriodicPaymentInitiationJson();
        actualBody.setExecutionRule(FiduciaExecutionRule.PRECEDING);
        ArgumentCaptor<Request.Builder> requestBuilderArgumentCaptor = ArgumentCaptor.forClass(Request.Builder.class);

        Mockito.verify(httpClient).send(requestBuilderArgumentCaptor.capture(), Mockito.any());
        assertThat(requestBuilderArgumentCaptor.getValue().body())
            .isEqualTo(new JacksonObjectMapper().writeValueAsString(actualBody));
    }

    @Test
    void getPeriodicPaymentInformationUsesFiduciaExecutionRule() {
        Mockito.doAnswer(invocationOnMock -> {
            ResponseHandler responseHandler = invocationOnMock.getArgument(1, ResponseHandler.class);
            String rawResponse = "{\"executionRule\":\"preceeding\"}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.emptyResponseHeaders()),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<PeriodicPaymentInitiationWithStatusResponse> adaptedResponse =
            fiduciaPaymentInitiationService.getPeriodicPaymentInformation(null,
                null,
                RequestHeaders.empty(),
                null);

        assertThat(adaptedResponse.getBody().getExecutionRule()).isEqualTo(ExecutionRule.PRECEDING);
    }

    @Test
    void getPeriodicPain001PaymentInformationUsesFiduciaExecutionRule() {
        Mockito.doAnswer(invocationOnMock -> {
            ResponseHandler responseHandler = invocationOnMock.getArgument(1, ResponseHandler.class);
            String rawResponse = "---\r\n" +
                "Content-Disposition: form-data; name=\"json_standingorderType\"\r\n" +
                "Content-Type: application/json\r\n\r\n" +
                "{\"executionRule\":\"preceeding\"}\r\n" +
                "-----\r\n";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "multipart/form-data; boundary=-"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<PeriodicPaymentInitiationMultipartBody> adaptedResponse =
            fiduciaPaymentInitiationService.getPeriodicPain001PaymentInformation(null,
                null,
                RequestHeaders.empty(),
                null);

        assertThat(adaptedResponse.getBody().getJson_standingorderType().getExecutionRule())
            .isEqualTo(ExecutionRule.PRECEDING);
    }
}
