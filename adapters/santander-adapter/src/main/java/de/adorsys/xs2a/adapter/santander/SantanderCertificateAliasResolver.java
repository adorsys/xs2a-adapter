/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

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
