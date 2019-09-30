package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;

public interface Oauth2ServiceFactory extends AdapterServiceProvider {
    Oauth2Service getOauth2Service(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore);
}
