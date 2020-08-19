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

package de.adorsys.xs2a.adapter.adorsys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
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

class AdorsysAccountInformationServiceWireMockTest {

    private static final String ACCOUNT_ID = "HGlNA7CqT8sjd_1aV2v2LI";
    private static final String CONSENT_ID = "LWigcCDnqIju2WxmN2QJiIIwDPJRCi55C92NUPa5IXRNg8JYEtRvNJAdwuefV7G4XcX1qLGcJAusajIQOAZagMz9MpaJIQIH3NJX8IHgetw=_=_psGLvQpt9Q";
    private static final String AUTHORISATION_ID = "19636dbe-99db-4d0f-90de-2573057bc7e4";

    private final ObjectMapper objectMapper = new JacksonObjectMapper().copyObjectMapper();
    private ResourceReader reader = JsonReader.getInstance(objectMapper);
    private static AdorsysAccountInformationService service;
    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(wireMockConfig()
                                                .port(8189)
                                                .usingFilesUnderClasspath("adorsys-adapter"));
        wireMockServer.start();

        HttpClient httpClient = new ApacheHttpClient(HttpClientBuilder.create().build());
        LinksRewriter linksRewriter = new IdentityLinksRewriter();
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("http://localhost:" + wireMockServer.port());

        service = new AdorsysAccountInformationService(aspsp, httpClient, null, linksRewriter);

    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    void createConsent() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/create-consent/request-headers.json", Map.class);
        Consents consents = reader.getObjectFromFile("ais/create-consent/request-body.json", Consents.class);
        ConsentsResponse201 expected = reader.getObjectFromFile("ais/create-consent/response-body.json", ConsentsResponse201.class);

        Response<ConsentsResponse201> response = service.createConsent(RequestHeaders.fromMap(headersMap), RequestParams.empty(), consents);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void updatePsuData() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/update-psu-data/request-headers.json", Map.class);
        UpdatePsuAuthentication psuAuthentication = reader.getObjectFromFile("ais/update-psu-data/request-body.json", UpdatePsuAuthentication.class);
        UpdatePsuAuthenticationResponse expected = reader.getObjectFromFile("ais/update-psu-data/response-body.json", UpdatePsuAuthenticationResponse.class);

        Response<UpdatePsuAuthenticationResponse> response = service.updateConsentsPsuData(CONSENT_ID,
                                                                                           AUTHORISATION_ID,
                                                                                           RequestHeaders.fromMap(headersMap),
                                                                                           RequestParams.empty(),
                                                                                           psuAuthentication);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void selectScaMethod() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/select-sca-method/request-headers.json", Map.class);
        SelectPsuAuthenticationMethod authenticationMethod = reader.getObjectFromFile("ais/select-sca-method/request-body.json", SelectPsuAuthenticationMethod.class);
        SelectPsuAuthenticationMethodResponse expected = reader.getObjectFromFile("ais/select-sca-method/response-body.json", SelectPsuAuthenticationMethodResponse.class);

        Response<SelectPsuAuthenticationMethodResponse> response = service.updateConsentsPsuData(CONSENT_ID,
                                                                                                 AUTHORISATION_ID,
                                                                                                 RequestHeaders.fromMap(headersMap),
                                                                                                 RequestParams.empty(),
                                                                                                 authenticationMethod);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void sendOtp() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/send-otp/request-headers.json", Map.class);
        TransactionAuthorisation authorisation = reader.getObjectFromFile("ais/send-otp/request-body.json", TransactionAuthorisation.class);
        ScaStatusResponse expected = reader.getObjectFromFile("ais/send-otp/response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.updateConsentsPsuData(CONSENT_ID,
                                                                             AUTHORISATION_ID,
                                                                             RequestHeaders.fromMap(headersMap),
                                                                             RequestParams.empty(),
                                                                             authorisation);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getScaStatus() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/get-sca-status/request-headers.json", Map.class);
        ScaStatusResponse expected = reader.getObjectFromFile("ais/get-sca-status/response-body.json", ScaStatusResponse.class);

        Response<ScaStatusResponse> response = service.getConsentScaStatus(CONSENT_ID,
                                                                           AUTHORISATION_ID,
                                                                           RequestHeaders.fromMap(headersMap),
                                                                           RequestParams.empty());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void deleteConsent() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/delete-consent/request-headers.json", Map.class);

        Response<Void> response = service.deleteConsent(CONSENT_ID,
                                                        RequestHeaders.fromMap(headersMap),
                                                        RequestParams.empty());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getAccounts() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/get-accounts/request-headers.json", Map.class);
        Map<String, String> paramsMap = reader.getObjectFromFile("ais/get-accounts/request-params.json", Map.class);
        AccountList expected = reader.getObjectFromFile("ais/get-accounts/response-body.json", AccountList.class);

        Response<AccountList> response = service.getAccountList(RequestHeaders.fromMap(headersMap), RequestParams.fromMap(paramsMap));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getBalances() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/get-balances/request-headers.json", Map.class);
        ReadAccountBalanceResponse200 expected = reader.getObjectFromFile("ais/get-balances/response-body.json", ReadAccountBalanceResponse200.class);

        Response<ReadAccountBalanceResponse200> response = service.getBalances(ACCOUNT_ID,
                                                                               RequestHeaders.fromMap(headersMap),
                                                                               RequestParams.empty());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void getTransactions() throws IOException {
        Map<String, String> headersMap = reader.getObjectFromFile("ais/get-transactions/request-headers.json", Map.class);
        Map<String, String> paramsMap = reader.getObjectFromFile("ais/get-transactions/request-params.json", Map.class);
        TransactionsResponse200Json expected = reader.getObjectFromFile("ais/get-transactions/response-body.json", TransactionsResponse200Json.class);

        Response<TransactionsResponse200Json> response = service.getTransactionList(ACCOUNT_ID,
                                                                                    RequestHeaders.fromMap(headersMap),
                                                                                    RequestParams.fromMap(paramsMap));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
