package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;

import java.util.Optional;

public class Psd2AdapterServiceLoader extends AdapterServiceLoader {

    public Psd2AdapterServiceLoader(AspspReadOnlyRepository aspspRepository,
                                    Pkcs12KeyStore keyStore,
                                    HttpClientFactory httpClientFactory,
                                    LinksRewriter accountInformationLinksRewriter,
                                    LinksRewriter paymentInitiationLinksRewriter,
                                    boolean chooseFirstFromMultipleAspsps) {
        super(aspspRepository, keyStore, httpClientFactory, accountInformationLinksRewriter,
            paymentInitiationLinksRewriter, chooseFirstFromMultipleAspsps);
    }

    public Psd2AccountInformationService getPsd2AccountInformationService(RequestHeaders requestHeaders) {
        Aspsp aspsp = getAspsp(requestHeaders);
        String adapterId = aspsp.getAdapterId();
        String baseUrl = aspsp.getUrl();
        Optional<Psd2AccountInformationServiceFactory> serviceProvider = getServiceProvider(Psd2AccountInformationServiceFactory.class, adapterId);
        if (!serviceProvider.isPresent()) {
            return new Xs2aPsd2AccountInformationServiceAdapter(getAccountInformationService(requestHeaders));
        }
        return serviceProvider
            .orElseThrow(() -> new AdapterNotFoundException(adapterId))
            .getAccountInformationService(baseUrl, httpClientFactory, keyStore);
    }
}
