package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;

public interface Psd2PaymentInitiationServiceFactory extends AdapterServiceProvider {

    Psd2PaymentInitiationService getPsd2PaymentInitiationService(String baseUrl,
                                                                 HttpClientFactory httpClientFactory,
                                                                 Pkcs12KeyStore keyStore,
                                                                 LinksRewriter linksRewriter);
}
