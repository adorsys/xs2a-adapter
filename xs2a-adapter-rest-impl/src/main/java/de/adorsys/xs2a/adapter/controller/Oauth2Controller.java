package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.rest.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.rest.psd2.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@RestController
public class Oauth2Controller {

    static final String AUTHORIZATION_REQUEST_URI = "/oauth2/authorization-request-uri";

    private final Oauth2Service oauth2Service;
    private final Oauth2Mapper mapper;

    public Oauth2Controller(Oauth2Service oauth2Service, Oauth2Mapper mapper) {
        this.oauth2Service = oauth2Service;
        this.mapper = mapper;
    }

    @GetMapping(AUTHORIZATION_REQUEST_URI)
    HrefType getAuthorizationUrl(@RequestHeader Map<String, String> headers, @RequestParam String state, @RequestParam("redirect_uri") URI redirectUri) throws IOException {
        URI authorizationUrl = oauth2Service.getAuthorizationRequestUri(headers, state, redirectUri);
        return new HrefType(authorizationUrl.toString());
    }

    @PostMapping("/oauth2/token")
    TokenResponseTO getToken(@RequestHeader Map<String, String> headers, @RequestParam("authorization_code") String authorizationCode) throws IOException {
        return mapper.map(oauth2Service.getToken(headers, authorizationCode));
    }
}
