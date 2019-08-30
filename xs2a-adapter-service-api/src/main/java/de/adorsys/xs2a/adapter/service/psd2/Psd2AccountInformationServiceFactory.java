package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.provider.AdapterServiceProvider;

public interface Psd2AccountInformationServiceFactory extends AdapterServiceProvider {

    Psd2AccountInformationService getAccountInformationService(String baseUrl, Pkcs12KeyStore keyStore);
}
