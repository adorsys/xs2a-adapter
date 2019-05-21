package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.http.HttpClient;
import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.ResponseHeaders;
import de.adorsys.xs2a.gateway.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.ais.Consents;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DeutscheBankAccountInformationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/ais/DE/SB-DB/v1";
    private static final String CONSENT_URL = BASE_URL + "/consents";
    private static final int HTTP_CODE_200 = 200;

    @SuppressWarnings("unchecked")
    @Test
    public void createConsent() {
        HttpClient httpClient = mock(HttpClient.class);
        DeutscheBankAccountInformationService service = new DeutscheBankAccountInformationService(BASE_URL);
        service.setHttpClient(httpClient);

        ArgumentCaptor<Map> headersCaptor = ArgumentCaptor.forClass(Map.class);

        when(httpClient.post(
                eq(CONSENT_URL),
                anyString(),
                headersCaptor.capture(),
                any()
        )).thenReturn(new GeneralResponse(HTTP_CODE_200, new ConsentCreationResponse(), ResponseHeaders.fromMap(Collections.emptyMap())));

        service.createConsent(RequestHeaders.fromMap(Collections.emptyMap()), new Consents());

        verify(httpClient, times(1)).post(
                eq(CONSENT_URL),
                anyString(),
                headersCaptor.capture(), any());

        Map headers = headersCaptor.getValue();

        assertThat(headers).hasSize(2);
        assertThat(headers.containsKey("Date")).isTrue();
        assertThat(headers.get("Content-Type")).isEqualTo("application/json");
    }
}