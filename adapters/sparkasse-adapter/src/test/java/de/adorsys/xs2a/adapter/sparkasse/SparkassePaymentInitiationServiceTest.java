package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SparkassePaymentInitiationServiceTest {

    private static final String PAYMENT_ID = "paymentId";
    private static final String AUTHORISATION_ID = "authorisationId";
    private static final String AUTHORISATION_TYPE = "PUSH_OTP";

    private final HttpClient client = mock(HttpClient.class);
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final ArgumentCaptor<Request.Builder> builderCaptor =
        ArgumentCaptor.forClass(Request.Builder.class);
    private final PaymentInitiationService service = new SparkasseServiceProvider()
        .getPaymentInitiationService(new Aspsp(), getHttpClientFactory(), new IdentityLinksRewriter());
    private final ClassLoader classLoader = getClass().getClassLoader();
    private Request.Builder requestBuilder;

    private HttpClientFactory getHttpClientFactory() {
        return new HttpClientFactory() {
            @Override
            public HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites) {
                return client;
            }

            @Override
            public HttpClientConfig getHttpClientConfig() {
                return httpClientConfig;
            }
        };
    }

    @BeforeEach
    void setUp() {
        requestBuilder = new RequestBuilderImpl(client, "", "");
    }

    @Test
    void initiatePayment() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/consentsPaymentInitiationResponse.json");

        when(client.post(anyString())).thenReturn(requestBuilder);
        when(client.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        String requestBody =  "<Document>\n" +
            "    <CstmrCdtTrfInitn>\n" +
            "        <PmtInf>\n" +
            "            <ReqdExctnDt>2020-07-10</ReqdExctnDt>\n" +
            "        </PmtInf>\n" +
            "        <PmtInf>\n" +
            "            <ReqdExctnDt>2020-07-10</ReqdExctnDt>\n" +
            "        </PmtInf>\n" +
            "    </CstmrCdtTrfInitn>\n" +
            "</Document>";

        Response<?> actualResponse = service.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            RequestParams.empty(),
            requestBody);

        verify(client, times(1)).send(builderCaptor.capture(), any());

        Request.Builder actualBuilder = builderCaptor.getValue();
        assertThat(actualBuilder)
            .isNotNull()
            .extracting(Request.Builder::body)
            .asString()
            .isXmlEqualTo("<Document>\n" +
                "    <CstmrCdtTrfInitn>\n" +
                "        <PmtInf>\n" +
                "            <ReqdExctnDt>1999-01-01</ReqdExctnDt>\n" +
                "        </PmtInf>\n" +
                "        <PmtInf>\n" +
                "            <ReqdExctnDt>1999-01-01</ReqdExctnDt>\n" +
                "        </PmtInf>\n" +
                "    </CstmrCdtTrfInitn>\n" +
                "</Document>");

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(PaymentInitationRequestResponse201.class))
            .matches(body -> {
                    var authObject = (AuthenticationObject) body.getChosenScaMethod();
                    return authObject.getAuthenticationType().equals(AUTHORISATION_TYPE);
            });
    }

    @Test
    void startPaymentAuthorisation() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/startScaprocessResponse.json");

        when(client.post(anyString())).thenReturn(requestBuilder);
        when(client.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = service.startPaymentAuthorisation(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.empty(),
            RequestParams.empty());

        verify(client, times(1)).post(anyString());
        verify(client, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(StartScaprocessResponse.class))
            .matches(body -> {
                    var authObject = (AuthenticationObject) body.getChosenScaMethod();
                    return authObject.getAuthenticationType().equals(AUTHORISATION_TYPE);
            })
            .matches(body ->
                body.getScaMethods()
                    .get(0) // assuming only one Sca Method
                    .getAuthenticationType()
                    .equals(AUTHORISATION_TYPE));
    }

    @Test
    void updatePaymentPsuData_updatePsuAuthentication() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/updatePsuAuthenticationResponse.json");

        when(client.put(anyString())).thenReturn(requestBuilder);
        when(client.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = service.updatePaymentPsuData(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new UpdatePsuAuthentication());

        verify(client, times(1)).put(anyString());
        verify(client, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(UpdatePsuAuthenticationResponse.class))
            .matches(body -> {
                var authObject = (AuthenticationObject) body.getChosenScaMethod();
                return authObject.getAuthenticationType().equals(AUTHORISATION_TYPE);
            })
            .matches(body ->
                body.getScaMethods()
                    .get(0) // assuming only one Sca Method
                    .getAuthenticationType()
                    .equals(AUTHORISATION_TYPE));
    }

    @Test
    void updatePaymentPsuData_selectScaMethod() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/selectPsuAuthenticationMethodResponse.json");

        when(client.put(anyString())).thenReturn(requestBuilder);
        when(client.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = service.updatePaymentPsuData(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new SelectPsuAuthenticationMethod());

        verify(client, times(1)).put(anyString());
        verify(client, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(SelectPsuAuthenticationMethodResponse.class))
            .matches(body -> {
                var authObject = (AuthenticationObject) body.getChosenScaMethod();
                return authObject.getAuthenticationType().equals(AUTHORISATION_TYPE);
            });
    }


    @SuppressWarnings("rawtypes")
    private Object getResponse(byte[] response, InvocationOnMock invocationOnMock) {
        HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
        return new Response<>(-1,
            responseHandler.apply(200,
                new ByteArrayInputStream(response),
                ResponseHeaders.emptyResponseHeaders()),
            null);
    }

    @SuppressWarnings("ConstantConditions")
    private byte[] readFile(String path) throws URISyntaxException, IOException {
        URI fileUri = classLoader.getResource(path).toURI();
        Path pathToFile = Paths.get(fileUri);
        return Files.readAllBytes(pathToFile);
    }
}
