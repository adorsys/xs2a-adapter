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

package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClient.ResponseHandler;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaDayOfExecution;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaExecutionRule;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaPeriodicPaymentInitiationJson;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FiduciaPaymentInitiationServiceTest {
    private FiduciaPaymentInitiationService fiduciaPaymentInitiationService;
    private HttpClient httpClient;
    private HttpClientFactory httpClientFactory;
    private HttpClientConfig httpClientConfig;

    @BeforeEach
    void setUp() {
        httpClient = spy(AbstractHttpClient.class);
        httpClientFactory = mock(HttpClientFactory.class);
        httpClientConfig = mock(HttpClientConfig.class);

        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        fiduciaPaymentInitiationService =
            new FiduciaPaymentInitiationService(new Aspsp(), httpClientFactory, null, new IdentityLinksRewriter());
    }

    @Test
    void initiatePaymentResponseUsesFiduciaChallengeData() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);

            //language=JSON
            String rawResponse = "{\n" +
                "  \"challengeData\": {\n" +
                "    \"data\": \"data\"\n" +
                "  }\n" +
                "}";
            return new Response<>(201,
                responseHandler.apply(201,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<PaymentInitationRequestResponse201> response = fiduciaPaymentInitiationService.initiatePayment(
            PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            null,
            new PaymentInitiationJson());

        assertThat(response.getBody().getChallengeData().getData().get(0)).isEqualTo("data");
    }

    @Test
    void updatePaymentPsuDataSelectPsuAuthenticationMethodResponseUsesFiduciaChallengeData() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);

            //language=JSON
            String rawResponse = "{\n" +
                "  \"challengeData\": {\n" +
                "    \"data\": \"data\"\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<SelectPsuAuthenticationMethodResponse> response = fiduciaPaymentInitiationService.updatePaymentPsuData(
            PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            null,
            null,
            RequestHeaders.empty(),
            null,
            new SelectPsuAuthenticationMethod());

        assertThat(response.getBody().getChallengeData().getData().get(0)).isEqualTo("data");
    }

    @Test
    void startPaymentAuthorisationStartScaprocessResponseUsesFiduciaChallengeData() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);

            //language=JSON
            String rawResponse = "{\n" +
                "  \"challengeData\": {\n" +
                "    \"data\": \"data\"\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<StartScaprocessResponse> response = fiduciaPaymentInitiationService.startPaymentAuthorisation(
            PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            null,
            RequestHeaders.empty(),
            null,
            new UpdatePsuAuthentication());

        assertThat(response.getBody().getChallengeData().getData().get(0)).isEqualTo("data");
    }

    @Test
    void startPaymentAuthorisationWithoutRequestBodyStartScaprocessResponseUsesFiduciaChallengeData() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);

            //language=JSON
            String rawResponse = "{\n" +
                "  \"challengeData\": {\n" +
                "    \"data\": \"data\"\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<StartScaprocessResponse> response = fiduciaPaymentInitiationService.startPaymentAuthorisation(
            PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            null,
            RequestHeaders.empty(),
            null);

        assertThat(response.getBody().getChallengeData().getData().get(0)).isEqualTo("data");
    }

    @Test
    void updatePaymentPsuDataUpdatePsuAuthenticationResponseUsesFiduciaChallengeData() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);

            //language=JSON
            String rawResponse = "{\n" +
                "  \"challengeData\": {\n" +
                "    \"data\": \"data\"\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<UpdatePsuAuthenticationResponse> response = fiduciaPaymentInitiationService.updatePaymentPsuData(
            PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            null,
            null,
            RequestHeaders.empty(),
            null,
            new UpdatePsuAuthentication());

        assertThat(response.getBody().getChallengeData().getData().get(0)).isEqualTo("data");
    }

    @Test
    void initiatePeriodicSctPaymentUsesFiduciaExecutionRule() {
        PeriodicPaymentInitiationJson body = new PeriodicPaymentInitiationJson();
        body.setExecutionRule(ExecutionRule.PRECEDING);
        Mockito.doReturn(new Response<>(201,
            new FiduciaPaymentInitationRequestResponse201(),
            null))
            .when(httpClient).send(Mockito.any(), Mockito.any());

        fiduciaPaymentInitiationService.initiatePayment(PaymentService.PERIODIC_PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
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
    void initiatePeriodicSctPaymentUsesFiduciaDayOfExecution() {
        PeriodicPaymentInitiationJson body = new PeriodicPaymentInitiationJson();
        body.setDayOfExecution(DayOfExecution._1);
        Mockito.doReturn(new Response<>(201,
            new FiduciaPaymentInitationRequestResponse201(),
            null))
            .when(httpClient).send(Mockito.any(), Mockito.any());

        fiduciaPaymentInitiationService.initiatePayment(PaymentService.PERIODIC_PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            null,
            body);

        FiduciaPeriodicPaymentInitiationJson actualBody = new FiduciaPeriodicPaymentInitiationJson();
        actualBody.setDayOfExecution(FiduciaDayOfExecution._1);
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
            fiduciaPaymentInitiationService.getPeriodicPaymentInformation(PaymentProduct.SEPA_CREDIT_TRANSFERS,
                null,
                RequestHeaders.empty(),
                null);

        assertThat(adaptedResponse.getBody().getExecutionRule()).isEqualTo(ExecutionRule.PRECEDING);
    }

    @Test
    void getPeriodicPaymentInformationUsesFiduciaDayOfExecution() {
        Mockito.doAnswer(invocationOnMock -> {
            ResponseHandler responseHandler = invocationOnMock.getArgument(1, ResponseHandler.class);
            String rawResponse = "{\"dayOfExecution\":\"01\"}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.emptyResponseHeaders()),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<PeriodicPaymentInitiationWithStatusResponse> adaptedResponse =
            fiduciaPaymentInitiationService.getPeriodicPaymentInformation(PaymentProduct.SEPA_CREDIT_TRANSFERS,
                null,
                RequestHeaders.empty(),
                null);

        assertThat(adaptedResponse.getBody().getDayOfExecution()).isEqualTo(DayOfExecution._1);
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
            fiduciaPaymentInitiationService.getPeriodicPain001PaymentInformation(PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
                null,
                RequestHeaders.empty(),
                null);

        assertThat(adaptedResponse.getBody().getJson_standingorderType().getExecutionRule())
            .isEqualTo(ExecutionRule.PRECEDING);
    }

    @Test
    void getPeriodicPain001PaymentInformationUsesFiduciaDayOfExecution() {
        Mockito.doAnswer(invocationOnMock -> {
            ResponseHandler responseHandler = invocationOnMock.getArgument(1, ResponseHandler.class);
            String rawResponse = "---\r\n" +
                "Content-Disposition: form-data; name=\"json_standingorderType\"\r\n" +
                "Content-Type: application/json\r\n\r\n" +
                "{\"dayOfExecution\":\"01\"}\r\n" +
                "-----\r\n";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "multipart/form-data; boundary=-"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<PeriodicPaymentInitiationMultipartBody> adaptedResponse =
            fiduciaPaymentInitiationService.getPeriodicPain001PaymentInformation(PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
                null,
                RequestHeaders.empty(),
                null);

        assertThat(adaptedResponse.getBody().getJson_standingorderType().getDayOfExecution())
            .isEqualTo(DayOfExecution._1);
    }
}
