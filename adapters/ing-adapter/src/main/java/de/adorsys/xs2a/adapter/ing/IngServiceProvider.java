package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class IngServiceProvider
    implements AccountInformationServiceProvider, Oauth2ServiceProvider, PaymentInitiationServiceProvider {

    private static final LinksRewriter DEFAULT_LINKS_REWRITER = new IdentityLinksRewriter();

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter,
                                                                  HttpLogSanitizer logSanitizer) {
        return getIngAccountInformationService(aspsp.getUrl(), httpClientFactory, keyStore, linksRewriter, logSanitizer);
    }

    private IngAccountInformationService getIngAccountInformationService(String baseUrl,
                                                                         HttpClientFactory httpClientFactory,
                                                                         Pkcs12KeyStore keyStore,
                                                                         LinksRewriter linksRewriter,
                                                                         HttpLogSanitizer logSanitizer) {
        HttpClient httpClient = httpClient(httpClientFactory, logSanitizer);
        IngAccountInformationApi accountInformationApi = new IngAccountInformationApi(baseUrl, httpClient, logSanitizer);
        IngOauth2Service ingOauth2Service = ingOauth2Service(baseUrl, httpClient, keyStore);
        return new IngAccountInformationService(accountInformationApi, ingOauth2Service, linksRewriter);

    }

    private HttpClient httpClient(HttpClientFactory httpClientFactory, HttpLogSanitizer logSanitizer) {
        return httpClientFactory.getHttpClient(getAdapterId(), AdapterConfig.readProperty("ing.qwac.alias"), logSanitizer);
    }

    private IngOauth2Service ingOauth2Service(String baseUri, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        IngOauth2Api oauth2Api = new IngOauth2Api(baseUri, httpClient);
        String qsealAlias = AdapterConfig.readProperty("ing.qseal.alias");
        return new IngOauth2Service(oauth2Api, clientAuthenticationFactory(keyStore, qsealAlias));
    }

    private IngClientAuthenticationFactory clientAuthenticationFactory(Pkcs12KeyStore keyStore, String qsealAlias) {
        try {
            X509Certificate qsealCertificate = keyStore.getQsealCertificate(qsealAlias);
            PrivateKey qsealPrivateKey = keyStore.getQsealPrivateKey(qsealAlias);
            return new IngClientAuthenticationFactory(qsealCertificate, qsealPrivateKey);
        } catch (GeneralSecurityException e) {
            throw new Xs2aAdapterException(e);
        }
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp,
                                          HttpClientFactory httpClientFactory,
                                          Pkcs12KeyStore keyStore,
                                          HttpLogSanitizer logSanitizer) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        return getIngAccountInformationService(baseUrl, httpClientFactory, keyStore, DEFAULT_LINKS_REWRITER, logSanitizer);
    }

    @Override
    public String getAdapterId() {
        return "ing-adapter";
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter,
                                                                HttpLogSanitizer logSanitizer) {

        HttpClient httpClient = httpClient(httpClientFactory, logSanitizer);
        IngOauth2Service ingOauth2Service = ingOauth2Service(aspsp.getUrl(), httpClient, keyStore);
        IngPaymentInitiationApi paymentInitiationApi = new IngPaymentInitiationApi(aspsp.getUrl(), httpClient, logSanitizer);
        return new IngPaymentInitiationService(paymentInitiationApi, ingOauth2Service, linksRewriter);
    }
}
