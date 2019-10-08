package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.api.Oauth2Api;
import de.adorsys.xs2a.adapter.mapper.psd2.Oauth2Mapper;
import de.adorsys.xs2a.adapter.rest.psd2.model.TokenResponseTO;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.psd2.model.HrefType;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

@RestController
public class Oauth2Controller implements Oauth2Api {

    private final Oauth2Service oauth2Service;
    private final Oauth2Mapper mapper = Mappers.getMapper(Oauth2Mapper.class);

    public Oauth2Controller(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @Override
    public HrefType getAuthorizationUrl(Map<String, String> headers, Map<String, String> parameters) throws IOException {
        URI authorizationUrl = oauth2Service.getAuthorizationRequestUri(headers, new Parameters(parameters));
        return new HrefType(authorizationUrl.toString());
    }

    @Override
    public TokenResponseTO getToken(Map<String, String> headers, Map<String, String> parameters) throws IOException {
        return mapper.map(oauth2Service.getToken(headers, new Parameters(parameters)));
    }
}
