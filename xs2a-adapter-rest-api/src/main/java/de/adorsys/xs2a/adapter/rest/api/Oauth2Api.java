package de.adorsys.xs2a.adapter.rest.api;

import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
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
                                 @RequestParam Map<String, String> parameters) throws IOException;

    @PostMapping("/oauth2/token")
    TokenResponseTO getToken(@RequestHeader Map<String, String> headers,
                             @RequestParam Map<String, String> parameters) throws IOException;
}
