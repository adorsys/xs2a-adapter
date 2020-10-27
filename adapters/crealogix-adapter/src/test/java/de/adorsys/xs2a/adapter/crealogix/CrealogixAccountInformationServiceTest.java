package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.PreAuthorisationException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CrealogixAccountInformationServiceTest {

    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_VALUE = "Bearer foo";
    private final HttpClient client = mock(HttpClient.class);
    private final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    private static final Aspsp aspsp = getAspsp();
    private AccountInformationService service;

    @BeforeEach
    void setUp() {
        service = new CrealogixAccountInformationService(aspsp, client, linksRewriter);
    }

    @Test
    void createConsent() {
        when(client.post(anyString())).thenReturn(new RequestBuilderImpl(client, "POST", ""));
        when(client.send(any(), any()))
            .thenReturn(new Response<>(
                -1,
                new ConsentsResponse201(),
                ResponseHeaders.emptyResponseHeaders()));

        Response<ConsentsResponse201> actualResponse
            = service.createConsent(
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty(),
                new Consents());

        verify(client, times(1)).post(anyString());
        verify(client, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(ConsentsResponse201.class);
    }

    @Test
    void createConsent_noAuthorizationHeader() {
        assertThatThrownBy(() -> service.createConsent(
            RequestHeaders.empty(),
            null,
            null))
            .isInstanceOf(PreAuthorisationException.class)
            .hasMessage(CrealogixPaymentInitiationService.ERROR_MESSAGE)
            .matches(er -> ((PreAuthorisationException) er)
                .getErrorResponse()
                .getLinks()
                .get("embeddedPreAuth")
                .getHref()
                .equals("/v1/embedded-pre-auth/token"));
    }

    @Test
    void createConsent_notAuthorized() {
        when(client.post(any())).thenReturn(new RequestBuilderImpl(client, "POST", ""));
        when(client.send(any(), any()))
            .thenThrow(new ErrorResponseException(-1, ResponseHeaders.emptyResponseHeaders()));

        assertThatThrownBy(() -> service.createConsent(
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty(),
            new Consents()))
            .isInstanceOf(ErrorResponseException.class);
    }

    private static Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }
}
