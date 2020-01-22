package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.Consents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.HttpMethod;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsorsAccountInformationServiceTest {

    private static final String PSU_ID = "psu id";
    private static final String QUOTES = "\"\"";

    private ConsorsAccountInformationService service;
    private Map<String, String> headerMap;

    @Mock
    private HttpClient httpClient;

    @Captor
    ArgumentCaptor<Map<String, String>> captor;

    @BeforeEach
    public void setUp() {
        headerMap = new HashMap<>();
        service = new ConsorsAccountInformationService(mock(Aspsp.class), httpClient);
    }

    @Test
    void createConsent_withPsuId() {
        headerMap.put(RequestHeaders.PSU_ID, PSU_ID);
        RequestHeaders headers = RequestHeaders.fromMap(headerMap);
        Request.Builder builder = spy(new RequestBuilderImpl(httpClient, HttpMethod.POST, null));

        when(httpClient.post(anyString())).thenReturn(builder);
        when(httpClient.send(any(), any())).thenReturn(new Response<>(201, null, null));

        service.createConsent(headers, new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(builder, times(1)).headers(captor.capture());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(captor.getValue());
        assertEquals(captor.getValue().get(RequestHeaders.PSU_ID), PSU_ID);
    }

    @Test
    void createConsent_noPsuId() {
        headerMap.put(RequestHeaders.PSU_ID, QUOTES);
        RequestHeaders headers = RequestHeaders.fromMap(headerMap);
        Request.Builder builder = spy(new RequestBuilderImpl(httpClient, HttpMethod.POST, null));

        when(httpClient.post(anyString())).thenReturn(builder);
        when(httpClient.send(any(), any())).thenReturn(new Response<>(201, null, null));

        service.createConsent(headers, new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(builder, times(1)).headers(captor.capture());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(captor.getValue());
        assertNull(captor.getValue().get(RequestHeaders.PSU_ID));
    }

    @Test
    void createConsent_psuIdNull() {
        headerMap.put(RequestHeaders.PSU_ID, null);
        RequestHeaders headers = RequestHeaders.fromMap(headerMap);
        Request.Builder builder = spy(new RequestBuilderImpl(httpClient, HttpMethod.POST, null));

        when(httpClient.post(anyString())).thenReturn(builder);
        when(httpClient.send(any(), any())).thenReturn(new Response<>(201, null, null));

        service.createConsent(headers, new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(builder, times(1)).headers(captor.capture());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(captor.getValue());
        assertNull(captor.getValue().get(RequestHeaders.PSU_ID));
    }

    @Test
    void createConsent_noPsuIdHeader() {
        RequestHeaders headers = RequestHeaders.fromMap(headerMap);
        Request.Builder builder = spy(new RequestBuilderImpl(httpClient, HttpMethod.POST, null));

        when(httpClient.post(anyString())).thenReturn(builder);
        when(httpClient.send(any(), any())).thenReturn(new Response<>(201, null, null));

        service.createConsent(headers, new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(builder, times(1)).headers(captor.capture());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(captor.getValue());
        assertTrue(captor.getValue().isEmpty());
    }
}
