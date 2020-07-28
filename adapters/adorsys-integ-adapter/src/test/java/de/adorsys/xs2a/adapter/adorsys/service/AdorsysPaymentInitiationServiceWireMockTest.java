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

import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.javatar.commons.reader.JsonReader;
import pro.javatar.commons.reader.ResourceReader;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AdorsysPaymentInitiationServiceWireMockTest {

    private static final String PAYMENT_ID = "W_pFk5-4OqzsXpxKLs9h97q8bfPnV3XKAm5MxM8dnT60LxXyPaGedv4HPQtEQ8-mcgftJbETkzvNvu5mZQqWcA==_=_psGLvQpt9Q";
    private static final String AUTHORISATION_ID = "259b8215-d14e-493a-ba01-c2243a9ff86a";
    private static final String PERIODIC_PAYMENT_ID = "RWPbX-Qgnmjb5yixTXwywIU9xxlutmt70MthNORZI9pVXXwjlE_8wK5HaZwohPtAcgftJbETkzvNvu5mZQqWcA==_=_psGLvQpt9Q";
    private static final String PERIODIC_AUTHORISATION_ID = "946d8445-7548-43e0-8cfc-f092d7ebc6cb";

    private final ObjectMapper objectMapper = new JacksonObjectMapper().copyObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    private ResourceReader reader = JsonReader.getInstance(objectMapper);
    private static PaymentInitiationService service;
    private static WireMockServer wireMockServer;
    private static final Ids paymentIds = new Ids()
        .add(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENT_ID)
        .add(PaymentService.PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
            "BWbshZUvuxnSwPNth2l-I3T0soaM3tozlyhq4pkpMd9eXNqj49jykOzF6X6Z1XdjcgftJbETkzvNvu5mZQqWcA==_=_psGLvQpt9Q")
        .add(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PERIODIC_PAYMENT_ID)
        .add(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
            "j2x8WKgv3GyF6Cin9XsD95hC83_mSJcZgIyS2ki92g7nLuRUsdKCSSW2nbnVA19OcgftJbETkzvNvu5mZQqWcA==_=_psGLvQpt9Q");
    private static final Ids authorisationIds = new Ids()
        .add(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, AUTHORISATION_ID)
        .add(PaymentService.PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
            "ebcc9e28-5edd-4ddb-8e22-12128067763d")
        .add(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PERIODIC_AUTHORISATION_ID)
        .add(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
            "5aa0b73f-6f89-45d9-90fe-1d679d95e6d6");

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(wireMockConfig()
                                                .port(8189)
                                                .usingFilesUnderClasspath("adorsys-integ-adapter"));
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
        Map<String, String> headersMap = reader.getObjectFromFile("pis/initiate-payment-request-headers.json", Map.class);
        PaymentInitiationJson paymentInitiationJson = reader.getObjectFromFile("pis/payments/sepa-credit-transfers/initiate-payment-request-body.json", PaymentInitiationJson.class);
        PaymentInitationRequestResponse201 expected = reader.getObjectFromFile("pis/payments/sepa-credit-transfers/initiate-payment-response-body.json", PaymentInitationRequestResponse201.class);

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PAYMENTS.toString(),
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                                                        RequestHeaders.fromMap(headersMap),
                                                                                        RequestParams.empty(),
                                                                                        paymentInitiationJson);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getScaStatus_Payments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/payments/get-sca-status/request-headers.json", Map.class);
        ScaStatusResponse expected = reader.getObjectFromFile("pis/payments/get-sca-status/response-body.json", ScaStatusResponse.class);

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
        Map<String, String> headersMap = reader.getObjectFromFile("pis/initiate-payment-request-headers.json", Map.class);
        PeriodicPaymentInitiationJson paymentInitiationJson = reader.getObjectFromFile("pis/periodic-payments/sepa-credit-transfers/initiate-payment-request-body.json", PeriodicPaymentInitiationJson.class);
        PaymentInitationRequestResponse201 expected = reader.getObjectFromFile("pis/periodic-payments/sepa-credit-transfers/initiate-payment-response-body.json", PaymentInitationRequestResponse201.class);

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PERIODIC_PAYMENTS.toString(),
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                                                        RequestHeaders.fromMap(headersMap),
                                                                                        RequestParams.empty(),
                                                                                        paymentInitiationJson);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getScaStatus_PeriodicPayments() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/periodic/get-sca-status/request-headers.json", Map.class);
        ScaStatusResponse expected = reader.getObjectFromFile("pis/periodic/get-sca-status/response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.getPaymentInitiationScaStatus(
            PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
            PERIODIC_PAYMENT_ID,
            PERIODIC_AUTHORISATION_ID,
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void initiatePayment_paymentsPain001Sct() throws IOException {
        Map<String, String> headersMap =
            reader.getObjectFromFile("pis/initiate-payment-request-headers.json", Map.class);
        String xml = reader.getStringFromFile("pis/payments/pain.001-sepa-credit-transfers/initiate-payment-request-body.xml");
        PaymentInitationRequestResponse201 expected =
            reader.getObjectFromFile("pis/payments/pain.001-sepa-credit-transfers/initiate-payment-response-body.json",
                PaymentInitationRequestResponse201.class);

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PAYMENTS.toString(),
            PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS.toString(),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            xml);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void initiatePayment_periodicPaymentsPain001Sct() throws IOException {
        Map<String, String> headersMap =
            reader.getObjectFromFile("pis/initiate-payment-request-headers.json", Map.class);
        PeriodicPaymentInitiationMultipartBody body =
            reader.getObjectFromFile("pis/periodic-payments/pain.001-sepa-credit-transfers/initiate-payment-request-body.json",
                PeriodicPaymentInitiationMultipartBody.class);
        PaymentInitationRequestResponse201 expected =
            reader.getObjectFromFile("pis/periodic-payments/pain.001-sepa-credit-transfers/initiate-payment-response-body.json",
                PaymentInitationRequestResponse201.class);

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(
            PaymentService.PERIODIC_PAYMENTS.toString(),
            PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS.toString(),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            body);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authenticatePsu(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/authenticate-psu-request-headers.json",
            Map.class);
        UpdatePsuAuthentication psuAuthentication = reader.getObjectFromFile("pis/authenticate-psu-request-body.json",
            UpdatePsuAuthentication.class);
        UpdatePsuAuthenticationResponse expected = reader.getObjectFromFile(
            "pis/" + paymentService + "/" + paymentProduct + "/authenticate-psu-response-body.json",
            UpdatePsuAuthenticationResponse.class);

        Response<UpdatePsuAuthenticationResponse> response = service.updatePaymentPsuData(
            paymentService.toString(),
            paymentProduct.toString(),
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            psuAuthentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    private static Stream<Arguments> paymentTypes() {
        return Stream.of(arguments(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS),
            arguments(PaymentService.PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS),
            arguments(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS),
            arguments(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS));
    }

    private static class Ids {
        private final Map<PaymentService, Map<PaymentProduct, String>> map = new EnumMap<>(PaymentService.class);

        Ids add(PaymentService paymentService, PaymentProduct paymentProduct, String id) {
            map.computeIfAbsent(paymentService, k -> new EnumMap<>(PaymentProduct.class))
                .put(paymentProduct, id);
            return this;
        }

        String get(PaymentService paymentService, PaymentProduct paymentProduct) {
            return Optional.ofNullable(map.get(paymentService))
                .map(m -> m.get(paymentProduct))
                .orElseThrow(NoSuchElementException::new);
        }
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void selectScaMethod(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/select-sca-method-request-headers.json", Map.class);
        SelectPsuAuthenticationMethod authenticationMethod = reader.getObjectFromFile("pis/select-sca-method-request-body.json",
            SelectPsuAuthenticationMethod.class);
        SelectPsuAuthenticationMethodResponse expected = reader.getObjectFromFile(
            "pis/" + paymentService + "/" + paymentProduct + "/select-sca-method-response-body.json",
            SelectPsuAuthenticationMethodResponse.class);

        Response<SelectPsuAuthenticationMethodResponse> response = service.updatePaymentPsuData(
            paymentService.toString(),
            paymentProduct.toString(),
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            authenticationMethod);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authoriseTransaction(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/authorise-transaction-request-headers.json", Map.class);
        TransactionAuthorisation authorisation = reader.getObjectFromFile("pis/authorise-transaction-request-body.json",
            TransactionAuthorisation.class);
        ScaStatusResponse expected = reader.getObjectFromFile(
            "pis/" + paymentService + "/" + paymentProduct + "/authorise-transaction-response-body.json",
            ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.updatePaymentPsuData(
            paymentService.toString(),
            paymentProduct.toString(),
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            authorisation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getTransactionStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/get-transaction-status-request-headers.json", Map.class);
        PaymentInitiationStatusResponse200Json expected = reader.getObjectFromFile(
            "pis/" + paymentService + "/" + paymentProduct + "/get-transaction-status-response-body.json",
            PaymentInitiationStatusResponse200Json.class);

        Response<PaymentInitiationStatusResponse200Json> response = service.getPaymentInitiationStatus(
            paymentService.toString(),
            paymentProduct.toString(),
            paymentIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
