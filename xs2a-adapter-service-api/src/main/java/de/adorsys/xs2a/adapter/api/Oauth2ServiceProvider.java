package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;

public interface Oauth2ServiceProvider extends AdapterServiceProvider {
    Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore);
}
