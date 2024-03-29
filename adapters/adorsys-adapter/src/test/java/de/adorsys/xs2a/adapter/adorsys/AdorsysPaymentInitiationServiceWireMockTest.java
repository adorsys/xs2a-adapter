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

package de.adorsys.xs2a.adapter.adorsys;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.impl.http.BaseHttpClientConfig;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.Xs2aHttpLogSanitizer;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
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

    private final ObjectMapper objectMapper = new JacksonObjectMapper().copyObjectMapper()
                                                  .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    private ResourceReader reader = JsonReader.getInstance(objectMapper);
    private static PaymentInitiationService service;
    private static WireMockServer wireMockServer;
    private static final Ids paymentIds = new Ids()
                                              .add(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENT_ID);
    private static final Ids authorisationIds = new Ids()
                                                    .add(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, AUTHORISATION_ID);

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(wireMockConfig()
                                                .port(8189)
                                                .usingFilesUnderClasspath("adorsys-adapter"));
        wireMockServer.start();


        HttpClient httpClient = new ApacheHttpClient(new Xs2aHttpLogSanitizer(), HttpClientBuilder.create().build());
        HttpClientConfig clientConfig = new BaseHttpClientConfig(null, null, null);
        HttpClientFactory httpClientFactory = getHttpClientFactory(httpClient, clientConfig);
        LinksRewriter linksRewriter = new IdentityLinksRewriter();
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("http://localhost:" + wireMockServer.port());
        service = new AdorsysIntegServiceProvider().getPaymentInitiationService(aspsp, httpClientFactory, linksRewriter);
    }

    private static HttpClientFactory getHttpClientFactory(HttpClient httpClient, HttpClientConfig config) {
        return new HttpClientFactory() {
            @Override
            public HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites) {
                return httpClient;
            }

            @Override
            public HttpClientConfig getHttpClientConfig() {
                return config;
            }
        };
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

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PAYMENTS,
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                                                        RequestHeaders.fromMap(headersMap),
                                                                                        RequestParams.empty(),
                                                                                        paymentInitiationJson);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getScaStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("pis/get-sca-status-request-headers.json", Map.class);
        ScaStatusResponse expected = reader.getObjectFromFile("pis/get-sca-status-response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.getPaymentInitiationScaStatus(
            paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
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
            paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty(),
            psuAuthentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    private static Stream<Arguments> paymentTypes() {
        return Stream.of(arguments(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS));
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
            paymentService,
            paymentProduct,
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
            paymentService,
            paymentProduct,
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
            paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            RequestHeaders.fromMap(headersMap),
            RequestParams.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
