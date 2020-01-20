package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;
import org.junit.jupiter.api.Test;

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
    private static final String WRONG_PSU_ID_TYPE = "PSU_ID_TYPE";
    private static final String DEFAULT_PSU_ID_TYPE = "HVB_ONLINEBANKING";
    private static final String ALTERNATIVE_PSU_ID_TYPE = "UCEBANKINGGLOBAL";

    @Test
    void createConsent_wrongPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, WRONG_PSU_ID_TYPE);

        HttpClient httpClient = mock(HttpClient.class);
        UnicreditAccountInformationService service = new UnicreditAccountInformationService(ASPSP, httpClient);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentCreationResponse(), ResponseHeaders.fromMap(headersMap)));

        service.createConsent(RequestHeaders.fromMap(headersMap), new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).isNotNull();
        assertThat(headers).isNotEmpty();
        assertThat(headers.get(RequestHeaders.PSU_ID_TYPE)).isEqualTo(DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void createConsent_defaultPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);

        HttpClient httpClient = mock(HttpClient.class);
        UnicreditAccountInformationService service = new UnicreditAccountInformationService(ASPSP, httpClient);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentCreationResponse(), ResponseHeaders.fromMap(headersMap)));

        service.createConsent(RequestHeaders.fromMap(headersMap), new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).isNotNull();
        assertThat(headers).isNotEmpty();
        assertThat(headers.get(RequestHeaders.PSU_ID_TYPE)).isEqualTo(DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void createConsent_alternativeAcceptedPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, ALTERNATIVE_PSU_ID_TYPE);

        HttpClient httpClient = mock(HttpClient.class);
        UnicreditAccountInformationService service = new UnicreditAccountInformationService(ASPSP, httpClient);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentCreationResponse(), ResponseHeaders.fromMap(headersMap)));

        service.createConsent(RequestHeaders.fromMap(headersMap), new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).isNotNull();
        assertThat(headers).isNotEmpty();
        assertThat(headers.get(RequestHeaders.PSU_ID_TYPE)).isEqualTo(ALTERNATIVE_PSU_ID_TYPE);
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }
}
