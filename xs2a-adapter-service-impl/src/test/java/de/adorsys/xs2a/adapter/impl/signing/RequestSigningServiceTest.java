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

package de.adorsys.xs2a.adapter.impl.signing;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.impl.signing.header.Digest;
import de.adorsys.xs2a.adapter.impl.signing.header.Signature;
import de.adorsys.xs2a.adapter.impl.signing.header.TppSignatureCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestSigningServiceTest {

    private static final X509Certificate certificate = generateCertificate();

    @Mock
    private Pkcs12KeyStore keyStore;

    @InjectMocks
    private RequestSigningService service;

    @Test
    void buildDigest() {
        Digest actual = service.buildDigest("body");

        assertNotNull(actual);
        assertEquals("Digest", actual.getHeaderName());
        assertEquals("SHA-256=Iw2DWNyOiJC0xY3utikS7i8gNXrpKlzIYbmOaP4xrLU=", actual.getHeaderValue());
    }

    @Test
    void buildSignature() throws KeyStoreException, UnsupportedEncodingException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException {
        Map<String, String> headers = new HashMap<>();
        headers.put("header", "header");

        when(keyStore.getQsealCertificate()).thenReturn(certificate);
        when(keyStore.getQsealPrivateKey()).thenReturn(generatePrivateKey());

        Signature actual = service.buildSignature(headers);

        verify(keyStore, times(1)).getQsealCertificate();
        verify(keyStore, times(1)).getQsealPrivateKey();

        assertNotNull(actual);
        assertEquals("Signature", actual.getHeaderName());
        assertTrue(actual.getHeaderValue().contains("keyId="));
        assertTrue(actual.getHeaderValue().contains("algorithm=\"SHA256withRSA\""));
    }

    @Test
    void buildTppSignatureCertificate() throws KeyStoreException, UnsupportedEncodingException, CertificateException {
        when(keyStore.getQsealCertificate()).thenReturn(certificate);

        TppSignatureCertificate actual = service.buildTppSignatureCertificate();

        verify(keyStore, times(1)).getQsealCertificate();

        assertNotNull(actual);
        assertEquals(RequestHeaders.TPP_SIGNATURE_CERTIFICATE, actual.getHeaderName());
        assertTrue(actual.getHeaderValue().contains("MIIBLTCB2KADAgECAgEDMA0GCSqGSI"));
    }

    private static X509Certificate generateCertificate() {
        X509Certificate certificate = null;
        CertificateFactory cf = null;

        String b64 =
            "-----BEGIN CERTIFICATE-----\n" +
                "MIIBLTCB2KADAgECAgEDMA0GCSqGSIb3DQEBBAUAMA0xCzAJBgNVBAMTAkNBMB4X\n" +
                "DTAyMTEwNzExNTcwM1oXDTIyMTEwNzExNTcwM1owFTETMBEGA1UEAxMKRW5kIEVu\n" +
                "dGl0eTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQDVBDfF+uBr5s5jzzDs1njKlZNt\n" +
                "h8hHzEt3ASh67Peos+QrDzgpUyFXT6fdW2h7iPf0ifjM8eW2xa+3EnPjjU5jAgMB\n" +
                "AAGjGzAZMBcGA1UdIAQQMA4wBgYEVR0gADAEBgIqADANBgkqhkiG9w0BAQQFAANB\n" +
                "AFo//WOboCNOCcA1fvcWW9oc4MvV8ZPvFIAbyEbgyFd4id5lGDTRbRPvvNZRvdsN\n" +
                "NM2gXYr+f87NHIXc9EF3pzw=\n" +
                "-----END CERTIFICATE-----";

        InputStream is = new ByteArrayInputStream(b64.getBytes(StandardCharsets.UTF_8));

        try {
            cf = CertificateFactory.getInstance("X.509");
            certificate = (X509Certificate) cf.generateCertificate(is);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

        return certificate;
    }

    private PrivateKey generatePrivateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.genKeyPair();

        return keyPair.getPrivate();
    }
}
