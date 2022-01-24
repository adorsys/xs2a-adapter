/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.ing.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.*;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IngPaymentInitiationServiceTest {

    private static final RequestHeaders emptyRequestHeaders = RequestHeaders.empty();
    private static final ResponseHeaders emptyResponseHeaders = ResponseHeaders.emptyResponseHeaders();
    private static final String PAYMENT_ID = "payment-id";

    private final HttpClient httpClient = spy(AbstractHttpClient.class);
    private final IngPaymentInitiationApi pia = spy(new IngPaymentInitiationApi(null, httpClient, null));
    private final IngOauth2Service oas = mock(IngOauth2Service.class);
    private final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    private IngPaymentInitiationService pis;

    @BeforeEach
    void setUp() {
        pis = new IngPaymentInitiationService(pia, oas, linksRewriter, null);
    }

    @Test
    void periodicPaymentInitiationWithUnsupportedFrequencyCausesValidationException() {
        PeriodicPaymentInitiationJson body = new PeriodicPaymentInitiationJson();
        body.setFrequency(FrequencyCode.MONTHLYVARIABLE);
        RequestParams requestParams = RequestParams.empty();

        RequestValidationException e = assertThrows(RequestValidationException.class,
            () -> pis.initiatePayment(PERIODIC_PAYMENTS, SEPA_CREDIT_TRANSFERS, emptyRequestHeaders, requestParams, body));
        assertThat(e.getValidationErrors())
            .anyMatch(validationError -> validationError.getCode() == ValidationError.Code.NOT_SUPPORTED
                && "frequency".equals(validationError.getPath()));
    }

    @Test
    void getPeriodicPain001PaymentInformationDeserialization() {
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
        }).when(httpClient).send(any(), any());
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
            pis.getPeriodicPain001PaymentInformation(PAIN_001_SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                emptyRequestHeaders,
                null);

        assertThat(response.getBody()).isEqualTo(expectedBody);
    }

    @Test
    void initiatePayment_xml() {
        doReturn(getResponse(new IngPaymentInitiationResponse()))
            .when(pia).initiatePaymentXml(any(), any(), any(), any(), anyList(), anyString());

        pis.initiatePayment(PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, emptyRequestHeaders, null, "<test></test>");

        verify(pia, times(1))
            .initiatePaymentXml(any(), any(), any(), any(), anyList(), anyString());
        verify(pia, never()).initiatePayment(any(), any(), any(), any(), anyList(), any());
    }

    @Test
    void initiatePayment_periodicXml() {
        doReturn(getResponse(new IngPeriodicPaymentInitiationResponse()))
            .when(pia).initiatePeriodicPaymentXml(any(), any(), any(), any(), anyList(), any(IngPeriodicPaymentInitiationXml.class));

        pis.initiatePayment(PERIODIC_PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            emptyRequestHeaders,
            null,
            new PeriodicPaymentInitiationMultipartBody());

        verify(pia, times(1))
            .initiatePeriodicPaymentXml(any(), any(), any(), any(), anyList(), any(IngPeriodicPaymentInitiationXml.class));
        verify(pia, never()).initiatePeriodicPayment(any(), any(), any(), any(), anyList(), any());
    }

    @Test
    void initiatePayment_unsupportedOperation() {

        assertThatThrownBy(() -> pis.initiatePayment(BULK_PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            emptyRequestHeaders,
            null,
            null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void getSinglePaymentInformation() {
        doReturn(getResponse(new IngPaymentInstruction()))
            .when(pia).getPaymentDetails(any(), anyString(), any(), any(), anyList());

        pis.getSinglePaymentInformation(SEPA_CREDIT_TRANSFERS, PAYMENT_ID, emptyRequestHeaders, null);

        verify(pia, times(1))
            .getPaymentDetails(any(), anyString(), any(), any(), anyList());
    }

    @Test
    void getPeriodicPaymentInformation() {
        doReturn(getResponse(new IngPeriodicPaymentInitiationJson()))
            .when(pia).getPeriodicPaymentDetails(any(), anyString(), any(), any(), anyList());

        pis.getPeriodicPaymentInformation(SEPA_CREDIT_TRANSFERS, PAYMENT_ID, emptyRequestHeaders, null);

        verify(pia, times(1))
            .getPeriodicPaymentDetails(any(), anyString(), any(), any(), anyList());
    }

    @Test
    void getPaymentInformationAsString_paymentsXml() {
        doReturn(getResponse("<response></response>"))
            .when(pia).getPaymentDetailsXml(any(), anyString(), any(), any(), anyList());

        pis.getPaymentInformationAsString(PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID, emptyRequestHeaders, null);

        verify(pia, times(1))
            .getPaymentDetailsXml(any(), anyString(), any(), any(), anyList());
        verify(pia, never())
            .getPaymentDetails(any(), anyString(), any(), any(), anyList());
    }

    @Test
    void getPaymentInformationAsString_periodicPaymentsXml() {
        doReturn(getResponse(new IngPeriodicPaymentInitiationXml()))
            .when(pia).getPeriodicPaymentDetailsXml(any(), anyString(), any(), any(), anyList());

        pis.getPaymentInformationAsString(PERIODIC_PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            emptyRequestHeaders,
            null);

        verify(pia, times(1))
            .getPeriodicPaymentDetailsXml(any(), anyString(), any(), any(), anyList());
        verify(pia, never())
            .getPeriodicPaymentDetails(any(), anyString(), any(), any(), anyList());
    }

    @Test
    void getPaymentInformationAsString_unsupportedOperation() {

        assertThatThrownBy(() -> pis.getPaymentInformationAsString(BULK_PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            emptyRequestHeaders,
            null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void getPaymentInitiationScaStatus() {

        assertThatThrownBy(() -> pis.getPaymentInitiationScaStatus(PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            "authorisation-id",
            emptyRequestHeaders,
            null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void getPaymentInitiationStatus_unsupportedOperation() {

        assertThatThrownBy(() -> pis.getPaymentInitiationStatus(BULK_PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            emptyRequestHeaders,
            null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void getPaymentInitiationStatusAsString_paymentsXml() {
        doReturn(getResponse("<response></response>"))
            .when(pia).getPaymentStatusXml(any(), anyString(), any(), any(), anyList());

        pis.getPaymentInitiationStatusAsString(PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            emptyRequestHeaders,
            null);

        verify(pia, times(1))
            .getPaymentStatusXml(any(), anyString(), any(), any(), anyList());
        verify(pia, never())
            .getPaymentStatus(any(), anyString(), any(), any(), anyList());
    }

    @Test
    void getPaymentInitiationStatusAsString_periodicPaymentsXml() {
        doReturn(getResponse("<response></response>"))
            .when(pia).getPeriodicPaymentStatusXml(any(), anyString(), any(), any(), anyList());

        pis.getPaymentInitiationStatusAsString(PERIODIC_PAYMENTS,
            PAIN_001_SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            emptyRequestHeaders,
            null);

        verify(pia, times(1))
            .getPeriodicPaymentStatusXml(any(), anyString(), any(), any(), anyList());
        verify(pia, never())
            .getPeriodicPaymentStatus(any(), anyString(), any(), any(), anyList());
    }

    @Test
    void getPaymentInitiationStatusAsString_unsupportedOperation() {

        assertThatThrownBy(() -> pis.getPaymentInitiationStatusAsString(BULK_PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            emptyRequestHeaders,
            null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void getPaymentInitiationAuthorisation() {

        assertThatThrownBy(() -> pis.getPaymentInitiationAuthorisation(null, null, null, null, null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void startPaymentAuthorisation() {

        assertThatThrownBy(() -> pis.startPaymentAuthorisation(null, null, null, null, null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void startPaymentAuthorisation_withPsuAuthentication() {

        assertThatThrownBy(() -> pis.startPaymentAuthorisation(null, null, null, null, null, null))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void updatePaymentPsuData_selectScaMethod() {
        SelectPsuAuthenticationMethod body = new SelectPsuAuthenticationMethod();

        assertThatThrownBy(() -> pis.updatePaymentPsuData(null,
            null,
            null,
            null,
            null,
            null,
            body))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void updatePaymentPsuData_authoriseTransaction() {
        TransactionAuthorisation body = new TransactionAuthorisation();

        assertThatThrownBy(() -> pis.updatePaymentPsuData(null,
            null,
            null,
            null,
            null,
            null,
            body))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    @Test
    void updatePaymentPsuData_updatePsuAuthentication() {
        UpdatePsuAuthentication body = new UpdatePsuAuthentication();

        assertThatThrownBy(() -> pis.updatePaymentPsuData(null,
            null,
            null,
            null,
            null,
            null,
            body))
            .isInstanceOf(UnsupportedOperationException.class);

        verifyNoInteractions(pia);
    }

    private <T> Response<T> getResponse(T body) {
        return new Response<>(200, body, emptyResponseHeaders);
    }
}
