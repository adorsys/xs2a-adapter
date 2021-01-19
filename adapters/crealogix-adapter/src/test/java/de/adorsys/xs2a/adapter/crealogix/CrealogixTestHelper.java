package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;

import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.mock;

public class CrealogixTestHelper {

    protected static final String AUTHORIZATION = "Authorization";
    protected static final String AUTHORIZATION_VALUE = getEncodedTokens();
    protected static final String TPP_TOKEN = "tppToken";
    protected static final String PSD2_AUTHORIZATION_TOKEN = "psd2AuthorizationToken";
    protected final HttpClient httpClient = mock(HttpClient.class);
    protected final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    protected final Aspsp aspsp = getAspsp();

    private static String getEncodedTokens() {
        CrealogixAuthorisationToken token
            = new CrealogixAuthorisationToken(TPP_TOKEN, PSD2_AUTHORIZATION_TOKEN);
        return token.encode();
    }

    protected  <T> Response<T> getResponse(T body) {
        return new Response<>(-1, body, ResponseHeaders.emptyResponseHeaders());
    }

    protected Request.Builder getRequestBuilder(String method) {
        return new RequestBuilderImpl(httpClient, method, "");
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }

    protected RequestHeaders getHeadersWithAuthorization() {
        return getHeadersWithAuthorization(AUTHORIZATION_VALUE);
    }

    protected RequestHeaders getHeadersWithAuthorization(String value) {
        return RequestHeaders.fromMap(singletonMap(AUTHORIZATION, value));
    }
}
