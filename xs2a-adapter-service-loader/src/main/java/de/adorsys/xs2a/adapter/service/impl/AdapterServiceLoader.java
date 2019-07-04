package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.exception.AdapterMappingNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdapterServiceLoader {
    private final AspspAdapterConfig aspspAdapterConfig;
    private final ConcurrentMap<Class<?>, ServiceLoader<? extends AdapterServiceProvider>> serviceLoaders = new ConcurrentHashMap<>();

    public AdapterServiceLoader() {
        this(new CsvAspspAdapterConfig());
    }

    public AdapterServiceLoader(AspspAdapterConfig aspspAdapterConfig) {
        this.aspspAdapterConfig = aspspAdapterConfig;
    }

    public AccountInformationService getAccountInformationService(String bic) {
        AspspAdapterConfigRecord aspspAdapterConfigRecord = getAspspAdapterConfigRecord(bic);
        String adapterId = aspspAdapterConfigRecord.getAdapterId();
        String baseUrl = aspspAdapterConfigRecord.getUrl();
        return getServiceProvider(AccountInformationServiceProvider.class, adapterId)
            .orElseThrow(() -> new AdapterNotFoundException(adapterId))
            .getAccountInformationService(baseUrl);
    }

    private AspspAdapterConfigRecord getAspspAdapterConfigRecord(String bic) {
        return aspspAdapterConfig.getAspspAdapterConfigRecord(bic)
            .orElseThrow(() -> new AdapterMappingNotFoundException(bic));
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

    public PaymentInitiationService getPaymentInitiationService(String bic) {
        AspspAdapterConfigRecord aspspAdapterConfigRecord = getAspspAdapterConfigRecord(bic);
        String adapterId = aspspAdapterConfigRecord.getAdapterId();
        String baseUrl = aspspAdapterConfigRecord.getUrl();
        return getServiceProvider(PaymentInitiationServiceProvider.class, adapterId)
            .orElseThrow(() -> new AdapterNotFoundException(adapterId))
            .getPaymentInitiationService(baseUrl);
    }

    public List<AspspAdapterConfigRecord> getSupportedAspsps() {
        return StreamSupport.stream(aspspAdapterConfig.spliterator(), false)
            .filter(r -> hasServiceProviders(r.getAdapterId()))
            .collect(Collectors.toList());
    }

    private boolean hasServiceProviders(String adapterId) {
        return getServiceProvider(AccountInformationServiceProvider.class, adapterId).isPresent()
            || getServiceProvider(PaymentInitiationServiceProvider.class, adapterId).isPresent();
    }
}
