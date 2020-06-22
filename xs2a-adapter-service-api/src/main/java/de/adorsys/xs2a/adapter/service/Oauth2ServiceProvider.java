package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;

public interface Oauth2ServiceProvider extends AdapterServiceProvider {
    Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore);
}
