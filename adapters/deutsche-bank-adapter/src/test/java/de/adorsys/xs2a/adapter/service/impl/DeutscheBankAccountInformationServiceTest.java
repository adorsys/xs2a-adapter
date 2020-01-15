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

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class DeutscheBankAccountInformationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/ais/DE/SB-DB";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private static final String CONSENT_URL = BASE_URL + "/v1/consents";

    @Test
    public void createConsent() {
        HttpClient httpClient = mock(HttpClient.class);
        DeutscheBankAccountInformationService service =
            new DeutscheBankAccountInformationService(ASPSP, httpClient, null);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentCreationResponse(), ResponseHeaders.fromMap(emptyMap())));

        service.createConsent(RequestHeaders.fromMap(emptyMap()), new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers).isNotNull();
        assertThat(headers).isNotEmpty();
        assertThat(headers).containsKey(RequestHeaders.DATE);
        assertThat(headers).containsKey(RequestHeaders.PSU_ID);
        assertThat(headers.get(RequestHeaders.CONTENT_TYPE)).isEqualTo("application/json");
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }
}
