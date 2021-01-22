package de.adorsys.xs2a.adapter.remote;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.remote.client.Oauth2Client;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RemoteOauth2Service implements Oauth2Service {

    private final Oauth2Client oauth2Client;
    private final Oauth2Mapper oauth2Mapper = Mappers.getMapper(Oauth2Mapper.class);

    public RemoteOauth2Service(Oauth2Client oauth2Client) {
        this.oauth2Client = oauth2Client;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        try {
            return new URI(this.oauth2Client.getAuthorizationUrl(headers, parameters.asMap()).getHref());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return this.oauth2Mapper.toTokenResponse(this.oauth2Client.getToken(headers, parameters.asMap()));
    }

}
