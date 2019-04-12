package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.ais.Consents;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DeutscheBankAccountInformationServiceTest {

    private static final String CONSENT_URL = "https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents";

    @SuppressWarnings("unchecked")
    @Test
    public void createConsent() {
        HttpClient httpClient = mock(HttpClient.class);
        DeutscheBankAccountInformationService service = new DeutscheBankAccountInformationService();
        service.setHttpClient(httpClient);

        ArgumentCaptor<Map> headersCaptor = ArgumentCaptor.forClass(Map.class);

        when(httpClient.post(
                eq(CONSENT_URL),
                anyString(),
                headersCaptor.capture(),
                any()
        )).thenReturn(new ConsentCreationResponse());

        service.createConsent(new Consents(), Headers.builder().build());

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