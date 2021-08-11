package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.exception.RequestAuthorizationValidationException;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SantanderAccountInformationServiceTest {

    private static final String URI = "https://foo.boo";
    private static final String ACCOUNT_ID = "accountId";
    private static final String REMITTANCE_INFORMATION_STRUCTURED = "remittanceInformationStructuredStringValue";
    private static final String CONSENT_ID = "consentId";

    private static final RequestHeaders HEADERS_WITH_AUTHORISATION
        = RequestHeaders.fromMap(Map.of(RequestHeaders.AUTHORIZATION, "Bearer foo"));
    private static Request.Builder requestBuilder;
    private static final RequestHeaders EMPTY_REQUEST_HEADERS = RequestHeaders.empty();
    private static final RequestParams EMPTY_REQUEST_PARAMS = RequestParams.empty();

    private SantanderAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;
    @Mock
    private SantanderOauth2Service oauth2Service;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private Pkcs12KeyStore keyStore;

    @BeforeEach
    void setUp() {
        requestBuilder = mock(Request.Builder.class);

        when(httpClientFactory.getHttpClient(any(), any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);

        accountInformationService = new SantanderAccountInformationService(aspsp, oauth2Service, httpClientFactory, linksRewriter);
    }



    @Test
    void getTransactionList() {
        String rawResponse = "{\n" +
            "  \"transactions\": {\n" +
            "    \"booked\": [\n" +
            "      {\n" +
            "        \"remittanceInformationStructured\": {" +
            "           \"reference\": \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "         }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(-1,
                    responseHandler.apply(200,
                        new ByteArrayInputStream(rawResponse.getBytes()),
                        ResponseHeaders.emptyResponseHeaders()),
                    null);
            });

        Response<?> actualResponse
            = accountInformationService.getTransactionList(ACCOUNT_ID, HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS);

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
    @SuppressWarnings("unchecked")
    void createConsent() {
        var requestBuilder = mock(Request.Builder.class);

        ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);

        when(httpClient.post(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.jsonBody(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(new ConsentsResponse201()));

        accountInformationService.createConsent(EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS, new Consents());

        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        Map<String, String> actualHeaders = headersCaptor.getValue();

        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(RequestHeaders.AUTHORIZATION, "Bearer null");
    }

    @Test
    void getConsentInformation() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(new ConsentInformationResponse200Json()));

        assertThatCode(() -> accountInformationService.getConsentInformation(CONSENT_ID, HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> accountInformationService.getConsentInformation(CONSENT_ID, EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void getConsentStatus() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(new ConsentStatusResponse200()));

        assertThatCode(() -> accountInformationService.getConsentStatus(CONSENT_ID, HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> accountInformationService.getConsentInformation(CONSENT_ID, EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void deleteConsent() {
        when(httpClient.delete(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(null));

        assertThatCode(() -> accountInformationService.deleteConsent(CONSENT_ID, HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> accountInformationService.deleteConsent(CONSENT_ID, EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void getAccountList() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(new AccountList()));

        assertThatCode(() -> accountInformationService.getAccountList(HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> accountInformationService.getAccountList(EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void readAccountDetails() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(new OK200AccountDetails()));

        assertThatCode(() -> accountInformationService.readAccountDetails(ACCOUNT_ID, HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> accountInformationService.readAccountDetails(ACCOUNT_ID, EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    private <T> Response<T> getResponse(T body) {
        return new Response<>(-1, body, ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void getBalances() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(getResponse(new ReadAccountBalanceResponse200()));

        assertThatCode(() -> accountInformationService.getBalances(ACCOUNT_ID, HEADERS_WITH_AUTHORISATION, EMPTY_REQUEST_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> accountInformationService.getBalances(ACCOUNT_ID, EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void getTransactionList_throwError() {
        assertThatThrownBy(() -> accountInformationService.getTransactionList(ACCOUNT_ID, EMPTY_REQUEST_HEADERS, EMPTY_REQUEST_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void throwUnsupportedOperationException() {
        assertThatThrownBy(() -> accountInformationService.startConsentAuthorisation(null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.startConsentAuthorisation(null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.updateConsentsPsuData(null, null, null, null, (UpdatePsuAuthentication) null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.updateConsentsPsuData(null, null, null, null, (SelectPsuAuthenticationMethod) null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.updateConsentsPsuData(null, null, null, null, (TransactionAuthorisation) null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getTransactionDetails(null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getConsentAuthorisation(null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getConsentAuthorisation(null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getCardAccountList(null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getCardAccountDetails(null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getCardAccountBalances(null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getCardAccountTransactionList(null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> accountInformationService.getConsentScaStatus(null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
    }
}
