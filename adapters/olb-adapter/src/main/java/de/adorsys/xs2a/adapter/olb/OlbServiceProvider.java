package de.adorsys.xs2a.adapter.olb;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

public class OlbServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter,
                                                                  HttpLogSanitizer logSanitizer) {
        return new OlbAccountInformationService(aspsp, httpClientFactory.getHttpClient(getAdapterId(), logSanitizer),
            linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter,
                                                                HttpLogSanitizer logSanitizer) {
        return new BasePaymentInitiationService(aspsp, httpClientFactory.getHttpClient(getAdapterId(), logSanitizer),
            linksRewriter);
    }

    @Override
    public String getAdapterId() {
        return "olb-adapter";
    }
}
