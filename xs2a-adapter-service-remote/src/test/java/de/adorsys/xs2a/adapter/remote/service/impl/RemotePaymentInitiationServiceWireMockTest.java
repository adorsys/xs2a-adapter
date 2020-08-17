package de.adorsys.xs2a.adapter.remote.service.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import de.adorsys.xs2a.adapter.api.model.ExecutionRule;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationMultipartBody;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationXmlPart2StandingorderTypeJson;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WireMockHttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.tpp.TppApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(classes = {TppApplication.class}, webEnvironment = NONE)
class RemotePaymentInitiationServiceWireMockTest {

    @Autowired
    RemotePaymentInitiationService remotePaymentInitiationService;


    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        int port = WireMockHttpClient.randomPort();
        System.setProperty("xs2a-adapter.url", "http://localhost:" + port);
        wireMockServer = new WireMockServer(wireMockConfig().port(port));
        wireMockServer.start();
        configureFor(wireMockServer.port());
        stubFor(get(urlEqualTo("/v1/periodic-payments/pain.001-sepa-credit-transfers/payment-id"))
            .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", "multipart/form-data; boundary=-")
                .withBody("---\r\n" +
                    "Content-Disposition: form-data; name=\"json_standingorderType\"\r\n" +
                    "Content-Type: application/json\r\n\r\n" +
                    "{\"executionRule\":\"following\"}\r\n" +
                    "---\r\n" +
                    "Content-Disposition: form-data; name=\"xml_sct\"\r\n" +
                    "Content-Type: application/xml\r\n\r\n" +
                    "<xml>\r\n" +
                    "-----\r\n")));
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    void getPeriodicPain001PaymentInformation() {
        Response<PeriodicPaymentInitiationMultipartBody> response =
            remotePaymentInitiationService.getPeriodicPain001PaymentInformation(
                PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS.toString(),
                "payment-id",
                RequestHeaders.empty(),
                RequestParams.empty());

        PeriodicPaymentInitiationMultipartBody expected = new PeriodicPaymentInitiationMultipartBody();
        expected.setXml_sct("<xml>");
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json =
            new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        json.setExecutionRule(ExecutionRule.FOLLOWING);
        expected.setJson_standingorderType(json);
        assertThat(response.getBody()).isEqualTo(expected);
    }
}
