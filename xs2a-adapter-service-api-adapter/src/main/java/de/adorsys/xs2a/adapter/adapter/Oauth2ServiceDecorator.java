package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.validation.ValidationError;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class Oauth2ServiceDecorator implements Oauth2Service {
    protected final Oauth2Service oauth2Service;

    public Oauth2ServiceDecorator(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        return oauth2Service.getAuthorizationRequestUri(headers, parameters);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return oauth2Service.getToken(headers, parameters);
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        return oauth2Service.validateGetAuthorizationRequestUri(headers, parameters);
    }

    @Override
    public List<ValidationError> validateGetToken(Map<String, String> headers, Parameters parameters) {
        return oauth2Service.validateGetToken(headers, parameters);
    }
}
