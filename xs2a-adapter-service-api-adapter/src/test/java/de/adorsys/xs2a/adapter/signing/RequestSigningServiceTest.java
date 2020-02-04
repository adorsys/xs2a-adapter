package de.adorsys.xs2a.adapter.signing;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.signing.header.Digest;
import de.adorsys.xs2a.adapter.signing.header.Signature;
import de.adorsys.xs2a.adapter.signing.header.TppSignatureCertificate;
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

        when(keyStore.getQsealCertificate()).thenReturn(generateCertificate());
        when(keyStore.getQsealPrivateKey()).thenReturn(genereatePrivateKey());

        Signature actual = service.buildSignature(headers);

        verify(keyStore, times(1)).getQsealCertificate();
        verify(keyStore, times(1)).getQsealPrivateKey();

        assertNotNull(actual);
        assertEquals(actual.getHeaderName(), "Signature");
        assertTrue(actual.getHeaderValue().contains("keyId="));
        assertTrue(actual.getHeaderValue().contains("algorithm=\"SHA256withRSA\""));
    }

    @Test
    void buildTppSignatureCertificate() throws KeyStoreException, UnsupportedEncodingException, CertificateException {
        when(keyStore.getQsealCertificate()).thenReturn(generateCertificate());

        TppSignatureCertificate actual = service.buildTppSignatureCertificate();

        verify(keyStore, times(1)).getQsealCertificate();

        assertNotNull(actual);
        assertEquals(actual.getHeaderName(), RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
        assertTrue(actual.getHeaderValue().contains("MIIBLTCB2KADAgECAgEDMA0GCSqGSI"));
    }

    private X509Certificate generateCertificate() throws UnsupportedEncodingException, CertificateException {
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
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate) cf.generateCertificate(is);
    }

    private PrivateKey genereatePrivateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = generator.genKeyPair();

        return keyPair.getPrivate();
    }
}
