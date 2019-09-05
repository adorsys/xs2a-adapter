package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.api.Oauth2Api;
import de.adorsys.xs2a.adapter.rest.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.rest.psd2.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
public class Oauth2Controller implements Oauth2Api {

    private final Oauth2Service oauth2Service;
    private final Oauth2Mapper mapper;

    public Oauth2Controller(Oauth2Service oauth2Service, Oauth2Mapper mapper) {
        this.oauth2Service = oauth2Service;
        this.mapper = mapper;
    }

    @Override
    public HrefType getAuthorizationUrl(Map<String, String> headers, String state, String redirectUri) throws IOException {
        URI uri;
        try {
            uri = new URI(redirectUri);
        } catch (URISyntaxException e) {
            throw new BadRequestException("\"redirect_uri\" is not a valid URI");
        }
        URI authorizationUrl = oauth2Service.getAuthorizationRequestUri(headers, state, uri);
        return new HrefType(authorizationUrl.toString());
    }

    @Override
    public TokenResponseTO getToken(Map<String, String> headers, String authorizationCode) throws IOException {
        return mapper.map(oauth2Service.getToken(headers, authorizationCode));
    }
}
