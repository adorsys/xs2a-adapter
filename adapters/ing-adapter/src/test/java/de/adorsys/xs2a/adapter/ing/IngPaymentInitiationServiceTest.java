package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngPaymentInitiationServiceTest {
    @Test
    void periodicPaymentInitiationWithUnsupportedFrequencyCausesValidationException() {
        IngPaymentInitiationService pis = new IngPaymentInitiationService(null, null, null);
        PeriodicPaymentInitiationJson body = new PeriodicPaymentInitiationJson();
        body.setFrequency(FrequencyCode.MONTHLYVARIABLE);
        String paymentService = PaymentService.PERIODIC_PAYMENTS.toString();
        String paymentProduct = PaymentProduct.SEPA_CREDIT_TRANSFERS.toString();
        RequestHeaders requestHeaders = RequestHeaders.empty();
        RequestParams requestParams = RequestParams.empty();

        RequestValidationException e = assertThrows(RequestValidationException.class,
            () -> pis.initiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body));
        assertThat(e.getValidationErrors())
            .anyMatch(validationError -> validationError.getCode() == ValidationError.Code.NOT_SUPPORTED
                && "frequency".equals(validationError.getPath()));
    }

    @Test
    void getPeriodicPain001PaymentInformationDeserialization() {
        HttpClient httpClient = Mockito.spy(AbstractHttpClient.class);
        IngPaymentInitiationApi pia = new IngPaymentInitiationApi(null, httpClient);
        IngPaymentInitiationService pis = new IngPaymentInitiationService(pia, Mockito.mock(IngOauth2Service.class), null);
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            String rawResponse = "---\r\n" +
                "Content-Disposition: form-data; name=\"xml_sct\"\r\n" +
                "Content-Type: application/xml\r\n\r\n" +
                "<xml>\r\n" +
                "---\r\n" +
                "Content-Disposition: form-data; name=\"startDate\"\r\n" +
                "Content-Type: text/plain\r\n\r\n" +
                "2020-02-20\r\n" +
                "---\r\n" +
                "Content-Disposition: form-data; name=\"endDate\"\r\n" +
                "Content-Type: text/plain\r\n\r\n" +
                "2020-02-22\r\n" +
                "---\r\n" +
                "Content-Disposition: form-data; name=\"frequency\"\r\n" +
                "Content-Type: text/plain\r\n\r\n" +
                "DAIL\r\n" +
                "---\r\n" +
                "Content-Disposition: form-data; name=\"dayOfExecution\"\r\n" +
                "Content-Type: text/plain\r\n\r\n" +
                "01\r\n" +
                "-----\r\n";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "multipart/form-data; boundary=-"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());
        PeriodicPaymentInitiationMultipartBody expectedBody = new PeriodicPaymentInitiationMultipartBody();
        expectedBody.setXml_sct("<xml>");
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json =
            new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        json.setStartDate(LocalDate.of(2020, 2, 20));
        json.setEndDate(LocalDate.of(2020, 2, 22));
        json.setFrequency(FrequencyCode.DAILY);
        json.setDayOfExecution(DayOfExecution._1);
        expectedBody.setJson_standingorderType(json);

        Response<PeriodicPaymentInitiationMultipartBody> response =
            pis.getPeriodicPain001PaymentInformation(PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS.toString(),
                "payment-id",
                RequestHeaders.empty(),
                null);

        assertThat(response.getBody()).isEqualTo(expectedBody);
    }
}
