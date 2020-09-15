package de.adorsys.xs2a.adapter.impl.http.wiremock;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WiremockStubDifferenceDetectingInterceptorTest {

    private Request.Builder.Interceptor interceptor;

    @Test
    void postHandle() {
        Aspsp aspsp = new Aspsp();
        aspsp.setName("adorsys-adapter");
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        interceptor = new WiremockStubDifferenceDetectingInterceptor(aspsp);
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "POST", "/v1/consents");
        request.jsonBody("{\"a\":12}");
        interceptor.postHandle(request, new Response<String>(200, "", ResponseHeaders.emptyResponseHeaders()));
    }
}
