package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SparkasseAccountInformationServiceTest {

    private static final String ACCOUNT_ID = "accountId";
    private static final String REMITTANCE_INFORMATION_STRUCTURED = "remittanceInformationStructuredStringValue";
    private static final String CONSENT_ID = "consentId";
    private static final String AUTHORISATION_ID = "authorisationId";

    private SparkasseAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;

    private final ClassLoader classLoader = getClass().getClassLoader();
    private Request.Builder requestBuilder;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        accountInformationService = new SparkasseAccountInformationService(aspsp, httpClientFactory, linksRewriter);
        requestBuilder = new RequestBuilderImpl(httpClient, "", "");
    }

    @Test
    void createConsent() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/consentsPaymentInitiationResponse.json");

        when(httpClient.post(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = accountInformationService.createConsent(RequestHeaders.empty(), RequestParams.empty(), new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(ConsentsResponse201.class))
            .matches(body ->
                body.getChosenScaMethod()
                    .getAuthenticationType()
                    .equals(AuthenticationType.PUSH_OTP));
    }

    @Test
    void startConsentAuthorisation() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/startScaprocessResponse.json");

        when(httpClient.post(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = accountInformationService.startConsentAuthorisation(CONSENT_ID, RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).post(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(StartScaprocessResponse.class))
            .matches(body ->
                body.getChosenScaMethod()
                    .getAuthenticationType()
                    .equals(AuthenticationType.PUSH_OTP))
            .matches(body ->
                body.getScaMethods()
                    .get(0) // assuming only one Sca Method
                    .getAuthenticationType()
                    .equals(AuthenticationType.PUSH_OTP));
    }

    @Test
    void updateConsentsPsuData_updatePsuAuthentication() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/updatePsuAuthenticationResponse.json");

        when(httpClient.put(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = accountInformationService.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new UpdatePsuAuthentication());

        verify(httpClient, times(1)).put(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(UpdatePsuAuthenticationResponse.class))
            .matches(body ->
                body.getChosenScaMethod()
                    .getAuthenticationType()
                    .equals(AuthenticationType.PUSH_OTP))
            .matches(body ->
                body.getScaMethods()
                    .get(0) // assuming only one Sca Method
                    .getAuthenticationType()
                    .equals(AuthenticationType.PUSH_OTP));
    }

    @Test
    void updateConsentsPsuData_selectScaMethod() throws IOException, URISyntaxException {
        byte[] response = readFile("responses/selectPsuAuthenticationMethodResponse.json");

        when(httpClient.put(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = accountInformationService.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new SelectPsuAuthenticationMethod());

        verify(httpClient, times(1)).put(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(SelectPsuAuthenticationMethodResponse.class))
            .matches(body ->
                body.getChosenScaMethod()
                    .getAuthenticationType()
                    .equals(AuthenticationType.PUSH_OTP));
    }

    @Test
    void getTransactionList() throws IOException, URISyntaxException {
        byte[] response = readFile("./responses/getTransactionList.json");

        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = accountInformationService.getTransactionList(ACCOUNT_ID, RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(TransactionsResponse200Json.class))
            .matches(body ->
                body.getTransactions()
                    .getBooked()
                    .get(0)
                    .getRemittanceInformationStructured()
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }

    @Test
    void getTransactionDetails() throws IOException, URISyntaxException {
        byte[] response = readFile("./responses/getTransactionDetails.json");

        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> getResponse(response, invocationOnMock));

        Response<?> actualResponse
            = accountInformationService.getTransactionDetails(ACCOUNT_ID,
            "transactionId",
            RequestHeaders.empty(),
            RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(OK200TransactionDetails.class))
            .matches(body ->
                body.getTransactionsDetails()
                    .getTransactionDetails()
                    .getRemittanceInformationStructured()
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
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
