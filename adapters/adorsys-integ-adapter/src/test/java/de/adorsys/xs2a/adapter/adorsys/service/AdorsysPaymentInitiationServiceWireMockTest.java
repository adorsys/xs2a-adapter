/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.adorsys.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.adorsys.service.provider.AdorsysIntegServiceProvider;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pro.javatar.commons.reader.JsonReader;
import pro.javatar.commons.reader.ResourceReader;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class AdorsysPaymentInitiationServiceWireMockTest {

    private static final String PAYMENT_ID = "W_pFk5-4OqzsXpxKLs9h97q8bfPnV3XKAm5MxM8dnT60LxXyPaGedv4HPQtEQ8-mcgftJbETkzvNvu5mZQqWcA==_=_psGLvQpt9Q";
    private static final String AUTHORISATION_ID = "259b8215-d14e-493a-ba01-c2243a9ff86a";
    private static final String PERIODIC_PAYMENT_ID = "RWPbX-Qgnmjb5yixTXwywIU9xxlutmt70MthNORZI9pVXXwjlE_8wK5HaZwohPtAcgftJbETkzvNvu5mZQqWcA==_=_psGLvQpt9Q";
    private static final String PERIODIC_AUTHORISATION_ID = "946d8445-7548-43e0-8cfc-f092d7ebc6cb";

    private final ObjectMapper objectMapper = new JacksonObjectMapper().copyObjectMapper();
    private ResourceReader reader = JsonReader.getInstance(objectMapper);
    private static PaymentInitiationService service;
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(wireMockConfig().port(8189));
        wireMockServer.start();


        HttpClient httpClient = new ApacheHttpClient(HttpClientBuilder.create().build());
        HttpClientFactory httpClientFactory = (adapterId, qwacAlias, supportedCipherSuites) -> httpClient;
        LinksRewriter linksRewriter = new IdentityLinksRewriter();
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("http://localhost:" + wireMockServer.port());

        service = new AdorsysIntegServiceProvider().getPaymentInitiationService(aspsp, httpClientFactory, null, linksRewriter);
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    void initiatePayment_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/initiate-payment/request-headers.json", Map.class);
        PaymentInitiationJson paymentInitiationJson = reader.getObjectFromFile("pis/payments/initiate-payment/request-body.json", PaymentInitiationJson.class);
        PaymentInitationRequestResponse201 expected = reader.getObjectFromFile("pis/payments/initiate-payment/response-body.json", PaymentInitationRequestResponse201.class);

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PAYMENTS.toString(),
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                                                        RequestHeaders.fromMap(headersMap),
                                                                                        RequestParams.empty(),
                                                                                        paymentInitiationJson);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void updatePsuData_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/update-psu-data/request-headers.json", Map.class);
        UpdatePsuAuthentication psuAuthentication = reader.getObjectFromFile("pis/payments/update-psu-data/request-body.json", UpdatePsuAuthentication.class);
        UpdatePsuAuthenticationResponse expected = reader.getObjectFromFile("pis/payments/update-psu-data/response-body.json", UpdatePsuAuthenticationResponse.class);

        Response<UpdatePsuAuthenticationResponse> response = service.updatePaymentPsuData(
            PaymentService.PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PAYMENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            psuAuthentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void selectScaMethod_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/select-sca-method/request-headers.json", Map.class);
        SelectPsuAuthenticationMethod authenticationMethod = reader.getObjectFromFile("pis/payments/select-sca-method/request-body.json", SelectPsuAuthenticationMethod.class);
        SelectPsuAuthenticationMethodResponse expected = reader.getObjectFromFile("pis/payments/select-sca-method/response-body.json", SelectPsuAuthenticationMethodResponse.class);

        Response<SelectPsuAuthenticationMethodResponse> response = service.updatePaymentPsuData(
            PaymentService.PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PAYMENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            authenticationMethod);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void sendOtp_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/send-otp/request-headers.json", Map.class);
        TransactionAuthorisation authorisation = reader.getObjectFromFile("pis/payments/send-otp/request-body.json", TransactionAuthorisation.class);
        ScaStatusResponse expected = reader.getObjectFromFile("pis/payments/send-otp/response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.updatePaymentPsuData(
            PaymentService.PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PAYMENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            authorisation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getTransactionStatus_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/get-transaction-status/request-headers.json", Map.class);
        PaymentInitiationStatusResponse200Json expected = reader.getObjectFromFile("pis/payments/get-transaction-status/response-body.json", PaymentInitiationStatusResponse200Json.class);

        Response<PaymentInitiationStatusResponse200Json> response = service.getPaymentInitiationStatus(
            PaymentService.PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PAYMENT_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    //    @Test
    // https://jira.adorsys.de/browse/XS2AAD-602 uncomment when task would be implemented
    void getStatus_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/get-status/request-headers.json", Map.class);
        ScaStatusResponse expected = reader.getObjectFromFile("pis/payments/get-status/response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.getPaymentInitiationScaStatus(
            PaymentService.PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PAYMENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void initiatePayment_PeriodicPayments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/periodic/initiate-payment/request-headers.json", Map.class);
        PeriodicPaymentInitiationJson paymentInitiationJson = reader.getObjectFromFile("pis/periodic/initiate-payment/request-body.json", PeriodicPaymentInitiationJson.class);
        PaymentInitationRequestResponse201 expected = reader.getObjectFromFile("pis/periodic/initiate-payment/response-body.json", PaymentInitationRequestResponse201.class);

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PERIODIC_PAYMENTS.toString(),
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                                                        RequestHeaders.fromMap(headersMap),
                                                                                        RequestParams.empty(),
                                                                                        paymentInitiationJson);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void updatePsuData_PeriodicPayments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/periodic/update-psu-data/request-headers.json", Map.class);
        UpdatePsuAuthentication psuAuthentication = reader.getObjectFromFile("pis/periodic/update-psu-data/request-body.json", UpdatePsuAuthentication.class);
        UpdatePsuAuthenticationResponse expected = reader.getObjectFromFile("pis/periodic/update-psu-data/response-body.json", UpdatePsuAuthenticationResponse.class);

        Response<UpdatePsuAuthenticationResponse> response = service.updatePaymentPsuData(
            PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PERIODIC_PAYMENT_ID,
            PERIODIC_AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            psuAuthentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }


    @Test
    void selectScaMethod_PeriodicPayments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/periodic/select-sca-method/request-headers.json", Map.class);
        SelectPsuAuthenticationMethod authenticationMethod = reader.getObjectFromFile("pis/periodic/select-sca-method/request-body.json", SelectPsuAuthenticationMethod.class);
        SelectPsuAuthenticationMethodResponse expected = reader.getObjectFromFile("pis/periodic/select-sca-method/response-body.json", SelectPsuAuthenticationMethodResponse.class);

        Response<SelectPsuAuthenticationMethodResponse> response = service.updatePaymentPsuData(
            PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PERIODIC_PAYMENT_ID,
            PERIODIC_AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            authenticationMethod);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void sendOtp_PeriodicPayments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/periodic/send-otp/request-headers.json", Map.class);
        TransactionAuthorisation authorisation = reader.getObjectFromFile("pis/periodic/send-otp/request-body.json", TransactionAuthorisation.class);
        ScaStatusResponse expected = reader.getObjectFromFile("pis/periodic/send-otp/response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.updatePaymentPsuData(
            PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PERIODIC_PAYMENT_ID,
            PERIODIC_AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            authorisation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getTransactionStatus_PeriodicPayments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/periodic/get-transaction-status/request-headers.json", Map.class);
        PaymentInitiationStatusResponse200Json expected = reader.getObjectFromFile("pis/periodic/get-transaction-status/response-body.json", PaymentInitiationStatusResponse200Json.class);

        Response<PaymentInitiationStatusResponse200Json> response = service.getPaymentInitiationStatus(
            PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PERIODIC_PAYMENT_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
