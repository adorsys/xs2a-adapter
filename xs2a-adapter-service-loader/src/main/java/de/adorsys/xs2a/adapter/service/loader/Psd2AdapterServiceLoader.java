package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;

import java.lang.reflect.Proxy;
import java.util.Optional;

public class Psd2AdapterServiceLoader extends AdapterServiceLoader {

    public Psd2AdapterServiceLoader(AspspReadOnlyRepository aspspRepository,
                                    Pkcs12KeyStore keyStore,
                                    HttpClientFactory httpClientFactory) {
        super(aspspRepository, keyStore, httpClientFactory);
    }

    public Psd2AccountInformationService getPsd2AccountInformationService(RequestHeaders requestHeaders) {
        Aspsp aspsp = getAspsp(requestHeaders);
        String adapterId = aspsp.getAdapterId();
        String baseUrl = aspsp.getUrl();
        Optional<Psd2AccountInformationServiceFactory> serviceProvider = getServiceProvider(Psd2AccountInformationServiceFactory.class, adapterId);
        Psd2AccountInformationService psd2AccountInformationService;
        if (!serviceProvider.isPresent()) {
            psd2AccountInformationService = new Xs2aPsd2AccountInformationServiceAdapter(getAccountInformationService(requestHeaders));
        } else {
            psd2AccountInformationService = serviceProvider
                                                .orElseThrow(() -> new AdapterNotFoundException(adapterId))
                                                .getAccountInformationService(baseUrl, httpClientFactory, keyStore);
        }

        return createProxy(psd2AccountInformationService);
    }

    private Psd2AccountInformationService createProxy(Psd2AccountInformationService psd2AccountInformationService) {
        ClassLoader classLoader = psd2AccountInformationService.getClass().getClassLoader();
        Class<?>[] interfaces = psd2AccountInformationService.getClass().getInterfaces();
        Psd2AccountInformationServiceInvocationHandler invocationHandler =
            new Psd2AccountInformationServiceInvocationHandler(psd2AccountInformationService);
        return (Psd2AccountInformationService) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
