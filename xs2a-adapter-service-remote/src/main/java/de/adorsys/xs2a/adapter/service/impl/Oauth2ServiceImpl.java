package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.api.remote.Oauth2Client;
import de.adorsys.xs2a.adapter.mapper.psd2.Oauth2Mapper;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class Oauth2ServiceImpl implements Oauth2Service {

    private final Oauth2Client oauth2Client;
    private final Oauth2Mapper oauth2Mapper = Mappers.getMapper(Oauth2Mapper.class);

    public Oauth2ServiceImpl(Oauth2Client oauth2Client) {
        this.oauth2Client = oauth2Client;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, String state, URI redirectUri) throws IOException {
        try {
            return new URI(this.oauth2Client.getAuthorizationUrl(headers, state, redirectUri.toString()).getHref());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return this.oauth2Mapper.toTokenResponse(this.oauth2Client.getToken(headers, parameters.asMap()));
    }

}
