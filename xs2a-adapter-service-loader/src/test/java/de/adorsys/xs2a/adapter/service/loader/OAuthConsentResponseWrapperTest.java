package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.OauthState;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;

import static de.adorsys.xs2a.adapter.service.loader.OAuthConsentResponseWrapper.SCA_OAUTH;
import static de.adorsys.xs2a.adapter.service.loader.OAuthConsentResponseWrapper.STATE_PARAMETER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OAuthConsentResponseWrapperTest {

    private static final String CLIENT_ID = "1111";
    private static final String ASPSP_ID = "2222";
    private static final String STATE_JSON = "{\"clientId\":\"" + CLIENT_ID + "\",\"aspspId\":\"" + ASPSP_ID + "\"}";
    private static final String SCA_OAUTH_LINK = "http://localhost:8082/oauth2/authorization-code?code=1234";
    private static final String ENCODED_STATE = "eyJjbGllbnRJZCI6IjExMTEiLCJhc3BzcElkIjoiMjIyMiJ9";

    @Test
    public void wrap() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.X_GTW_ASPSP_ID, ASPSP_ID);
        headers.put(RequestHeaders.PSU_ID, CLIENT_ID);

        ConsentsResponse consent = new ConsentsResponse();
        consent.setLinks(new HashMap<>());
        consent.getLinks().put(SCA_OAUTH, new HrefType(SCA_OAUTH_LINK));

        // case when state is not present in the link

        Response<ConsentsResponse> consentResponse = new Response<>(200, consent, ResponseHeaders.fromMap(headers));
        Response<ConsentsResponse> response = OAuthConsentResponseWrapper.wrap(headers, consentResponse);

        assertThat(response.getBody().getLinks().get(SCA_OAUTH).getHref(), is(SCA_OAUTH_LINK + STATE_PARAMETER + ENCODED_STATE));

        // case when state already present in the link

        String oldState = STATE_PARAMETER + "old-state";
        consent.getLinks().put(SCA_OAUTH, new HrefType(SCA_OAUTH_LINK + oldState));
        response = OAuthConsentResponseWrapper.wrap(headers, consentResponse);

        assertThat(response.getBody().getLinks().get(SCA_OAUTH).getHref(), is(SCA_OAUTH_LINK + STATE_PARAMETER + ENCODED_STATE));
    }

    @Test(expected = IllegalStateException.class)
    public void wrapWithException() {
        HashMap<String, String> headers = new HashMap<>();

        ConsentsResponse consent = new ConsentsResponse();
        consent.setLinks(new HashMap<>());
        consent.getLinks().put(SCA_OAUTH, new HrefType(SCA_OAUTH_LINK));

        Response<ConsentsResponse> consentResponse = new Response<>(200, consent, ResponseHeaders.fromMap(headers));
        OAuthConsentResponseWrapper.wrap(headers, consentResponse);
    }

    @Test
    public void encodeState() {
        OauthState state = new OauthState(CLIENT_ID, ASPSP_ID);

        assertThat(state.toJson(), is(STATE_JSON));
        String encodedState = Base64.getEncoder().encodeToString(STATE_JSON.getBytes());

        assertThat(OAuthConsentResponseWrapper.encodeState(state), is(encodedState));
    }
}
