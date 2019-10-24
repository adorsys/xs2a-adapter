package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.Consents;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.HashMap;

import static de.adorsys.xs2a.adapter.service.loader.OAuthConsentResponseWrapper.SCA_OAUTH;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Psd2AccountInformationServiceInvocationHandlerTest {

    @Test
    public void invoke() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.X_GTW_ASPSP_ID, "aspspID");
        headers.put(RequestHeaders.PSU_ID, "clientID");

        ConsentsResponse consent = new ConsentsResponse();
        consent.setLinks(new HashMap<>());
        consent.getLinks().put(SCA_OAUTH, new HrefType("http://localhost:8082/oauth2/authorization-code?code=1234"));

        // case when state is not present in the link

        Response<ConsentsResponse> consentResponse = new Response<>(200, consent, ResponseHeaders.fromMap(headers));


        Psd2AccountInformationService informationService = mock(Psd2AccountInformationService.class);

        Psd2AccountInformationServiceInvocationHandler handler
            = new Psd2AccountInformationServiceInvocationHandler(informationService);

        ClassLoader classLoader = informationService.getClass().getClassLoader();
        Class<?>[] interfaces = informationService.getClass().getInterfaces();

        when(informationService.createConsent(anyMap(), anyObject())).thenReturn(consentResponse);

        Psd2AccountInformationService proxy = (Psd2AccountInformationService) Proxy.newProxyInstance(classLoader, interfaces, handler);

        Response<ConsentsResponse> response = proxy.createConsent(headers, new Consents());

        String scaOauth = response.getBody().getLinks().get(SCA_OAUTH).getHref();
        assertThat(scaOauth.contains("&state="), is(true));
    }
}
