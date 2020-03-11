package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2ServiceFactory;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;

import java.security.GeneralSecurityException;

public class IngServiceFactory implements Psd2AccountInformationServiceFactory, Oauth2ServiceFactory {
    private static final LinksRewriter DEFAULT_LINKS_REWRITER = new IdentityLinksRewriter();

    @Override
    public Psd2AccountInformationService getAccountInformationService(String baseUrl,
                                                                      HttpClientFactory httpClientFactory,
                                                                      Pkcs12KeyStore keyStore,
                                                                      LinksRewriter linksRewriter) {
        return getIngPsd2AccountInformationService(baseUrl, httpClientFactory, keyStore, linksRewriter);
    }

    private IngPsd2AccountInformationService getIngPsd2AccountInformationService(String baseUrl,
                                                                                 HttpClientFactory httpClientFactory,
                                                                                 Pkcs12KeyStore keyStore,
                                                                                 LinksRewriter linksRewriter) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId(), AdapterConfig.readProperty("ing.qwac.alias"));
        try {
            return new IngPsd2AccountInformationService(baseUrl, httpClient, keyStore, linksRewriter);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp,
                                          HttpClientFactory httpClientFactory,
                                          Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        return getIngPsd2AccountInformationService(baseUrl, httpClientFactory, keyStore, DEFAULT_LINKS_REWRITER);
    }

    @Override
    public String getAdapterId() {
        return "ing-adapter";
    }
}
