package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class BaseAccountInformationServiceTest {

    private static final String BASE_URI = "baseUri";
    private static final String IDP_URL = "https://idp.url";
    private static final String CONSENTID = "consentId";
    private static final String AUTHORISATIONID = "authorisationId";
    private static final String ACCOUNTID = "accountId";
    public static final String PSU_AUTHORISATION_URI = BASE_URI + "/v1/consents/" + CONSENTID;
    public static final String UPDATE_PSU_AUTHORISATION_URI = PSU_AUTHORISATION_URI +
        "/authorisations/" + AUTHORISATIONID;
    public static final String TRANSACTION_LIST_URI = BASE_URI + "/v1/accounts/" + ACCOUNTID
        + "/transactions";

    private static final Aspsp ASPSP = buildAspspWithUrls();
    private static Consents body = buildConsent();
    private static UpdatePsuAuthentication updatePsuAuthentication = new UpdatePsuAuthentication();
    private static RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());
    private static RequestParams params = RequestParams.builder().build();

    private BaseAccountInformationService informationService;

    @Mock
    private HttpClient httpClient;

    @Spy
    private Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, null, null);

    @Mock
    private Request.Builder.Interceptor interceptor;

    @Captor
    ArgumentCaptor<Map<String, String>> headersCaptor;

    @Captor
    ArgumentCaptor<String> uriCaptor, bodyCaptor;

    @Captor
    ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        informationService = new BaseAccountInformationService(ASPSP, httpClient, interceptor);
    }

    @Test
    void createConsent() {
        ConsentCreationResponse example = new ConsentCreationResponse();
        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<ConsentCreationResponse> response = informationService.createConsent(headers, params, body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(BASE_URI + "/v1/consents");
        assertThat(headersCaptor.getValue()).isEqualTo(informationService.addPsuIdHeader(headers.toMap()));
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getConsentInformation() {
        ConsentInformation example = new ConsentInformation();
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<ConsentInformation> response = informationService.getConsentInformation(CONSENTID, headers, params);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PSU_AUTHORISATION_URI);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void deleteConsent() {
        when(httpClient.delete(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(Void.class)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        informationService.deleteConsent(CONSENTID, headers, params);

        verify(httpClient, times(1)).delete(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PSU_AUTHORISATION_URI);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
    }

    @Test
    void getConsentStatus() {
        ConsentStatusResponse example = new ConsentStatusResponse();
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<ConsentStatusResponse> response = informationService.getConsentStatus(CONSENTID, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PSU_AUTHORISATION_URI + "/status");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void startConsentAuthorisation() {
        StartScaProcessResponse example = new StartScaProcessResponse();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<StartScaProcessResponse> response = informationService.startConsentAuthorisation(CONSENTID, headers);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).emptyBody(booleanArgumentCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PSU_AUTHORISATION_URI + "/authorisations");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(booleanArgumentCaptor.getValue()).isTrue();
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void startConsentAuthorisation_updatePsuAuthentication() {
        StartScaProcessResponse example = new StartScaProcessResponse();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<StartScaProcessResponse> response = informationService.startConsentAuthorisation(CONSENTID, headers, updatePsuAuthentication);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PSU_AUTHORISATION_URI + "/authorisations");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void updateConsentsPsuData_updatePsuAuthentication() {
        UpdatePsuAuthenticationResponse example = new UpdatePsuAuthenticationResponse();

        when(httpClient.put(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<UpdatePsuAuthenticationResponse> response
            = informationService.updateConsentsPsuData(CONSENTID, AUTHORISATIONID, headers, updatePsuAuthentication);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PSU_AUTHORISATION_URI);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void updateConsentsPsuData_selectPsuAuthenticationMethodResponse() {
        SelectPsuAuthenticationMethod selectPsuAuthenticationMethod = new SelectPsuAuthenticationMethod();
        SelectPsuAuthenticationMethodResponse example = new SelectPsuAuthenticationMethodResponse();

        when(httpClient.put(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<SelectPsuAuthenticationMethodResponse> response
            = informationService.updateConsentsPsuData(CONSENTID, AUTHORISATIONID, headers, selectPsuAuthenticationMethod);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PSU_AUTHORISATION_URI);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void updateConsentsPsuData_transactionAuthorisation() {
        TransactionAuthorisation transactionAuthorisation = new TransactionAuthorisation();
        ScaStatusResponse example = new ScaStatusResponse();

        when(httpClient.put(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<ScaStatusResponse> response
            = informationService.updateConsentsPsuData(CONSENTID, AUTHORISATIONID, headers, transactionAuthorisation);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PSU_AUTHORISATION_URI);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getAccountList() {
        AccountListHolder example = new AccountListHolder();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<AccountListHolder> response
            = informationService.getAccountList(headers, params);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(AbstractService.buildUri(BASE_URI + "/v1/accounts", params));
        assertThat(headersCaptor.getValue()).isEqualTo(informationService.addConsentIdHeader(headers.toMap()));
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getTransactionList() {
        TransactionsReport example = new TransactionsReport();
        Map<String, String> transactionHeaders = headers.toMap();
        transactionHeaders.put(RequestHeaders.ACCEPT, ContentType.APPLICATION_JSON);

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<TransactionsReport> response
            = informationService.getTransactionList(ACCOUNTID, headers, params);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(AbstractService.buildUri(TRANSACTION_LIST_URI, params));
        assertThat(headersCaptor.getValue()).isEqualTo(transactionHeaders);
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getTransactionDetails() {
        TransactionDetails example = new TransactionDetails();
        String transactionId = "transactionId";

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<TransactionDetails> response
            = informationService.getTransactionDetails(ACCOUNTID, transactionId, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(TRANSACTION_LIST_URI + "/" + transactionId);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getTransactionListAsString() {
        String example = "list of transactions";

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<String> response
            = informationService.getTransactionListAsString(ACCOUNTID, headers, params);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(AbstractService.buildUri(TRANSACTION_LIST_URI, params));
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getConsentScaStatus() {
        ScaStatusResponse example = new ScaStatusResponse();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<ScaStatusResponse> response
            = informationService.getConsentScaStatus(CONSENTID, AUTHORISATIONID, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PSU_AUTHORISATION_URI);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getBalances() {
        BalanceReport example = new BalanceReport();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<BalanceReport> response
            = informationService.getBalances(ACCOUNTID, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(BASE_URI + "/v1/accounts/" + ACCOUNTID + "/balances");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    public void getConsentBaseUri() {

        assertThat(informationService.getConsentBaseUri()).isEqualTo("baseUri/v1/consents");
    }

    @Test
    public void getAccountsBaseUri() {

        assertThat(informationService.getAccountsBaseUri()).isEqualTo("baseUri/v1/accounts");
    }

    @Test
    void getIdpUri() {
        assertThat(informationService.getIdpUri()).isEqualTo(IDP_URL);
    }

    private static Aspsp buildAspspWithUrls() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URI);
        aspsp.setIdpUrl(IDP_URL);
        return aspsp;
    }

    private static Consents buildConsent() {
        return new Consents();
    }

    private <T> Response<T> dummyResponse(T body) {
        return new Response<>(0, body, null);
    }
}
