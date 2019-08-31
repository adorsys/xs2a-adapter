package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationServiceFactory;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class IngAccountInformationServiceFactory implements Psd2AccountInformationServiceFactory {
    @Override
    public Psd2AccountInformationService getAccountInformationService(String baseUrl, Pkcs12KeyStore keyStore) {
        try {
            return new IngPsd2AccountInformationService(baseUrl, keyStore);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException | UnrecoverableEntryException | IOException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAdapterId() {
        return "ing-adapter";
    }
}