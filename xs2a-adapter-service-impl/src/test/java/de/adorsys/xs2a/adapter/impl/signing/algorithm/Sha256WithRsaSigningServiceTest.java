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

package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import de.adorsys.xs2a.adapter.api.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.SigningAlgorithm.SigningService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Sha256WithRsaSigningServiceTest {
    private static final String STRING_TO_SIGN = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    private static final String EXPECTED_SIGNED_VALUE = "5161f9cea2a270d8a6f1b86b50a43c6960d4fe45030b8636680ba33963ff" +
        "e76772d1c7b1c736ad32fa108946fbaab3bb20f6791117b60f11bf063d5f4fe4698f4cb3290f5a6a3b0d11f30ab61ac359516c103fc7" +
        "d3e38bc6d9c9a10e2d99be111536fc2d1c86a4f75727dbb24e0be5a7ad949b74ec77c89d7f16e2a7a6b2f6a5b8e5d3f982f9e646da88" +
        "4783f80ac517faa091cb6c9e38de8a37e85e95c0803517d4f848f9872f0d75301dcdf545da37408e22e985e7398e31b4949526cc034e" +
        "5b97b8a9be47c79e0a1c20228dff8ea04567b1ed6575efa96972921922086d8995307255bb0ad606a2a98175854b5867f86ab17c6d36" +
        "df68d97a7cd014456e3a3dad21bc519328a14d89b73eaf8868959150588c914b371b4f33e5b698844139db1f7979b7fd41bada51219e" +
        "edbf021c632bca08425a4aa57784ee9d8954acb0e61b5686a3b8bf7b9af6b0527ef93c87b31e11bcd43575a10aba1763dfc526392a06" +
        "da9f77d92270e27577eddcefe877b7779882331b5ddd8388331fb27810de2520c6bf78f59eeb7318efa950d76bfbaea46846fe60200d" +
        "8195c2eec0c64362e7412120b927e4a7e171d1c93a808abbc705ac38cc9f7a385fc0829ab2f9213b0a91480599b7f7798c666906bbbd" +
        "596c85005c259e65c29a2d67903a6b9e6526a590e35c2a5018d96783c901dbe898620f8bd835c9b2918a467ae5fc0f5e001b";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private static final String PATH_TO_RSA_TEST_CERTIFICATE_PROPERTIES = "/rsa-test-certificate.properties";

    private static final Properties CERTIFICATE_PROPERTIES = readCertificateProperties();
    private static final String CERTIFICATE_PATH_PROPERTY = "certificate.path";
    private static final String CERTIFICATE_TYPE_PROPERTY = "certificate.type";
    private static final String CERTIFICATE_PASSWORD_PROPERTY = "certificate.password";

    private static final PrivateKey PRIVATE_KEY = readPrivateKey();

    @Test
    void sign() {
        SigningService signingService = SigningAlgorithm.SHA256_WITH_RSA.getSigningService();

        byte[] actualSignedValue = signingService.sign(PRIVATE_KEY, STRING_TO_SIGN, UTF8_CHARSET);

        assertThat(toHex(actualSignedValue)).isEqualTo(EXPECTED_SIGNED_VALUE);
    }

    @Test
    void sign_throwsException() {
        SigningService signingService = SigningAlgorithm.SHA256_WITH_RSA.getSigningService();

        assertThrows(HttpRequestSigningException.class, () -> signingService.sign(null, "data", UTF8_CHARSET));
    }

    private static Properties readCertificateProperties() {
        Properties properties = new Properties();

        try {
            properties.load(getResourceAsStream(PATH_TO_RSA_TEST_CERTIFICATE_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    private static InputStream getResourceAsStream(String path) {
        return Sha256WithRsaSigningServiceTest.class.getResourceAsStream(path);
    }

    public static PrivateKey readPrivateKey() {
        try {
            String certificatePassword = CERTIFICATE_PROPERTIES.getProperty(CERTIFICATE_PASSWORD_PROPERTY);

            KeyStore keystore = KeyStore.getInstance(CERTIFICATE_PROPERTIES.getProperty(CERTIFICATE_TYPE_PROPERTY));
            keystore.load(
                getResourceAsStream(CERTIFICATE_PROPERTIES.getProperty(CERTIFICATE_PATH_PROPERTY)),
                certificatePassword.toCharArray()
            );

            return (PrivateKey) keystore.getKey(keystore.aliases().nextElement(), certificatePassword.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String toHex(byte[] hashedValue) {
        StringBuilder sb = new StringBuilder();

        for (byte b : hashedValue) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }

        return sb.toString();
    }
}
