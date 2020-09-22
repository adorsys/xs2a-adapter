package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UnicreditAccountInformationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/ais/DE/SB-DB";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private static final String CONSENT_URL = BASE_URL + "/v1/consents";
    public static final String CONSENT_ID = "consent-id";
    private static final String CONSENT_ID_URL = CONSENT_URL + "/" + CONSENT_ID;
    private static final String WRONG_PSU_ID_TYPE = "PSU_ID_TYPE";
    private static final String DEFAULT_PSU_ID_TYPE = "HVB_ONLINEBANKING";
    private static final String ALTERNATIVE_PSU_ID_TYPE = "UCEBANKINGGLOBAL";
    public static final String AUTHORISATION_ID = "authorisation-id";
    public static final String AUTHORISATION_URL = CONSENT_ID_URL + "?authenticationCurrentNumber=" + AUTHORISATION_ID;
    private static final String TPP_REDIRECT_URI = "http://example.com";
    private HttpClient httpClient;
    private LinksRewriter linksRewriter;
    private UnicreditAccountInformationService accountInformationService;

    @BeforeEach
    void setUp() {
        httpClient = mock(HttpClient.class);
        linksRewriter = mock(LinksRewriter.class);
        accountInformationService = new UnicreditAccountInformationService(ASPSP, httpClient, linksRewriter);
    }

    @Test
    void createConsent_wrongPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, WRONG_PSU_ID_TYPE);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentsResponse201(), ResponseHeaders.fromMap(headersMap)));

        accountInformationService.createConsent(RequestHeaders.fromMap(headersMap),
                                                RequestParams.empty(),
                                                new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void createConsent_defaultPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentsResponse201(), ResponseHeaders.fromMap(headersMap)));

        accountInformationService.createConsent(RequestHeaders.fromMap(headersMap),
                                                RequestParams.empty(),
                                                new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void createConsent_alternativeAcceptedPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, ALTERNATIVE_PSU_ID_TYPE);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentsResponse201(), ResponseHeaders.fromMap(headersMap)));

        accountInformationService.createConsent(RequestHeaders.fromMap(headersMap),
                                                RequestParams.empty(),
                                                new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).containsEntry(RequestHeaders.PSU_ID_TYPE, ALTERNATIVE_PSU_ID_TYPE);
    }

    @Test
    void updateConsentsPsuData() {
        Request.Builder requestBuilder = spy(new RequestBuilderImpl(httpClient, "PUT", AUTHORISATION_URL));
        ScaStatusResponse statusResponse = new ScaStatusResponse();

        when(httpClient.put(anyString())).thenReturn(requestBuilder);
        doReturn(new Response<>(200,
                                statusResponse,
                                ResponseHeaders.fromMap(Collections.emptyMap()))).when(requestBuilder).send(any(), eq(Collections.emptyList()));

        accountInformationService.updateConsentsPsuData(CONSENT_ID,
                                                        AUTHORISATION_ID,
                                                        RequestHeaders.fromMap(Collections.emptyMap()),
                                                        RequestParams.empty(),
                                                        new TransactionAuthorisation());

        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }
}
