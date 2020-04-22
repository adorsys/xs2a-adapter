package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2ServiceFactory;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.ing.internal.api.AccountInformationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthenticationFactory;
import de.adorsys.xs2a.adapter.service.ing.internal.api.Oauth2Api;
import de.adorsys.xs2a.adapter.service.ing.internal.api.PaymentInitiationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationServiceFactory;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class IngServiceFactory implements Psd2AccountInformationServiceFactory, Oauth2ServiceFactory,
    Psd2PaymentInitiationServiceFactory {

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
        HttpClient httpClient = httpClient(httpClientFactory);
        AccountInformationApi accountInformationApi = new AccountInformationApi(baseUrl, httpClient);
        IngOauth2Service ingOauth2Service = ingOauth2Service(baseUrl, httpClient, keyStore);
        return new IngPsd2AccountInformationService(accountInformationApi, ingOauth2Service, linksRewriter);

    }

    private HttpClient httpClient(HttpClientFactory httpClientFactory) {
        return httpClientFactory.getHttpClient(getAdapterId(), AdapterConfig.readProperty("ing.qwac.alias"));
    }

    private IngOauth2Service ingOauth2Service(String baseUri, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        Oauth2Api oauth2Api = new Oauth2Api(baseUri, httpClient);
        String qsealAlias = AdapterConfig.readProperty("ing.qseal.alias");
        return new IngOauth2Service(oauth2Api, clientAuthenticationFactory(keyStore, qsealAlias));
    }

    private ClientAuthenticationFactory clientAuthenticationFactory(Pkcs12KeyStore keyStore, String qsealAlias) {
        try {
            X509Certificate qsealCertificate = keyStore.getQsealCertificate(qsealAlias);
            PrivateKey qsealPrivateKey = keyStore.getQsealPrivateKey(qsealAlias);
            return new ClientAuthenticationFactory(qsealCertificate, qsealPrivateKey);
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

    @Override
    public Psd2PaymentInitiationService getPsd2PaymentInitiationService(String baseUrl,
                                                                        HttpClientFactory httpClientFactory,
                                                                        Pkcs12KeyStore keyStore,
                                                                        LinksRewriter linksRewriter) {

        HttpClient httpClient = httpClient(httpClientFactory);
        IngOauth2Service ingOauth2Service = ingOauth2Service(baseUrl, httpClient, keyStore);
        PaymentInitiationApi paymentInitiationApi = new PaymentInitiationApi(baseUrl, httpClient);
        return new IngPsd2PaymentInitiationService(paymentInitiationApi, ingOauth2Service, linksRewriter);
    }
}
