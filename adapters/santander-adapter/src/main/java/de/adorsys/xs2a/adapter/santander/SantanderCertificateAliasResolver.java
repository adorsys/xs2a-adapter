package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

public class SantanderCertificateAliasResolver {

    private static final Logger logger = LoggerFactory.getLogger(SantanderCertificateAliasResolver.class);

    private static final String SANTANDER_QWAC_ALIAS = "santander_qwac";

    private SantanderCertificateAliasResolver() { }

    // For Sandbox environment, Adapter will try to get Santander certificates.
    public static String getCertificateAlias(Pkcs12KeyStore keyStore) {
        try {
            X509Certificate certificate = keyStore.getQsealCertificate(SANTANDER_QWAC_ALIAS); // works for QWAC
            if (certificate != null) {
                logger.info("Santander Sandbox certificate will be used");
                return SANTANDER_QWAC_ALIAS;
            }
        } catch (KeyStoreException e) {
            logger.error("Failed to get a certificate from the KeyStore", e);
        }

        // Indicating using default certificate
        logger.info("Using default QWAC certificate");
        return null;
    }
}
