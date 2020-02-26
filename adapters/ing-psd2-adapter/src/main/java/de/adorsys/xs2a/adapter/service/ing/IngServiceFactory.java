package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2ServiceFactory;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;

import java.security.GeneralSecurityException;

public class IngServiceFactory implements Psd2AccountInformationServiceFactory, Oauth2ServiceFactory {
    @Override
    public Psd2AccountInformationService getAccountInformationService(String baseUrl,
                                                                      HttpClientFactory httpClientFactory,
                                                                      Pkcs12KeyStore keyStore) {
        return getIngPsd2AccountInformationService(baseUrl, httpClientFactory, keyStore);
    }

    private IngPsd2AccountInformationService getIngPsd2AccountInformationService(String baseUrl,
                                                                                 HttpClientFactory httpClientFactory,
                                                                                 Pkcs12KeyStore keyStore) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId(), AdapterConfig.readProperty("ing.qwac.alias"));
        try {
            return new IngPsd2AccountInformationService(baseUrl, httpClient, keyStore);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp,
                                          HttpClientFactory httpClientFactory,
                                          Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        return getIngPsd2AccountInformationService(baseUrl, httpClientFactory, keyStore);
    }

    @Override
    public String getAdapterId() {
        return "ing-adapter";
    }
}
