package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.rest.psd2.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

public interface Oauth2Api {

    String AUTHORIZATION_REQUEST_URI = "/oauth2/authorization-request-uri";

    @GetMapping(AUTHORIZATION_REQUEST_URI)
    HrefType getAuthorizationUrl(@RequestHeader Map<String, String> headers,
                                 @RequestParam("state") String state,
                                 @RequestParam("redirect_uri") String redirectUri) throws IOException;

    @PostMapping("/oauth2/token")
    TokenResponseTO getToken(@RequestHeader Map<String, String> headers,
                             @RequestParam("authorization_code") String authorizationCode,
                             @RequestParam("redirect_uri") String redirectUri,
                             @RequestParam("client_id") String clientId) throws IOException;
}
