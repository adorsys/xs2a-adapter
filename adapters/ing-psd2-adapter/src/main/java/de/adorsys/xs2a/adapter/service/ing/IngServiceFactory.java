package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2ServiceFactory;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class IngServiceFactory implements Psd2AccountInformationServiceFactory, Oauth2ServiceFactory {
    @Override
    public Psd2AccountInformationService getAccountInformationService(String baseUrl, Pkcs12KeyStore keyStore) {
        return getIngPsd2AccountInformationService(baseUrl, keyStore);
    }

    private IngPsd2AccountInformationService getIngPsd2AccountInformationService(String baseUrl, Pkcs12KeyStore keyStore) {
        try {
            return new IngPsd2AccountInformationService(baseUrl, keyStore);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | UnrecoverableEntryException | IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Oauth2Service getOauth2Service(String baseUrl, Pkcs12KeyStore keyStore) {
        return getIngPsd2AccountInformationService(baseUrl, keyStore);
    }

    @Override
    public String getAdapterId() {
        return "ing-adapter";
    }
}
