package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

class DeutscheBankAccountInformationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/ais/DE/SB-DB";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private static final String CONSENT_URL = BASE_URL + "/v1/consents";

    @Test
    void createConsent() {
        HttpClient httpClient = mock(HttpClient.class);
        DeutscheBankAccountInformationService service =
            new DeutscheBankAccountInformationService(ASPSP, httpClient, null, new IdentityLinksRewriter(), null);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentsResponse201(), ResponseHeaders.fromMap(emptyMap())));

        service.createConsent(RequestHeaders.fromMap(emptyMap()), RequestParams.empty(), new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers)
            .isNotNull()
            .isNotEmpty()
            .containsKey(RequestHeaders.DATE)
            .containsKey(RequestHeaders.PSU_ID)
            .containsEntry(RequestHeaders.CONTENT_TYPE, "application/json");
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }
}
