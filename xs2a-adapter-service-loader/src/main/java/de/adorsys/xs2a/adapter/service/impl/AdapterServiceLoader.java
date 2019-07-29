package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.exception.AdapterMappingNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.StreamSupport;

public class AdapterServiceLoader {
    private final AspspRepository aspspRepository;
    private final ConcurrentMap<Class<?>, ServiceLoader<? extends AdapterServiceProvider>> serviceLoaders = new ConcurrentHashMap<>();

    public AdapterServiceLoader(AspspRepository aspspRepository) {
        this.aspspRepository = aspspRepository;
    }

    public AccountInformationService getAccountInformationService(String aspspId) {
        Aspsp aspsp = getAspsp(aspspId);
        String adapterId = aspsp.getAdapterId();
        String baseUrl = aspsp.getUrl();
        return getServiceProvider(AccountInformationServiceProvider.class, adapterId)
            .orElseThrow(() -> new AdapterNotFoundException(adapterId))
            .getAccountInformationService(baseUrl);
    }

    private Aspsp getAspsp(String aspspId) {
        return aspspRepository.findById(aspspId)
            .orElseThrow(() -> new AdapterMappingNotFoundException(aspspId));
    }

    public <T extends AdapterServiceProvider> Optional<T> getServiceProvider(Class<T> klass, String adapterId) {
        ServiceLoader<T> serviceLoader = getServiceLoader(klass);

        return StreamSupport.stream(serviceLoader.spliterator(), false)
            .filter(provider -> provider.getAdapterId().equalsIgnoreCase(adapterId))
            .findFirst();
    }

    @SuppressWarnings("unchecked")
    private <T extends AdapterServiceProvider> ServiceLoader<T> getServiceLoader(Class<T> klass) {
        return (ServiceLoader<T>) serviceLoaders.computeIfAbsent(klass, k -> ServiceLoader.load(klass));
    }

    public PaymentInitiationService getPaymentInitiationService(String aspspId) {
        Aspsp aspspAdapterConfigRecord = getAspsp(aspspId);
        String adapterId = aspspAdapterConfigRecord.getAdapterId();
        String baseUrl = aspspAdapterConfigRecord.getUrl();
        return getServiceProvider(PaymentInitiationServiceProvider.class, adapterId)
            .orElseThrow(() -> new AdapterNotFoundException(adapterId))
            .getPaymentInitiationService(baseUrl);
    }
}
