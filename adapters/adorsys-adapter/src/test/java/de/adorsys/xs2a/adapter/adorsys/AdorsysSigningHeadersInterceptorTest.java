package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdorsysSigningHeadersInterceptorTest {

    private static final String CERTIFICATE = "-----BEGIN CERTIFICATE-----\n" +
        "MIIBLTCB2KADAgECAgEDMA0GCSqGSIb3DQEBBAUAMA0xCzAJBgNVBAMTAkNBMB4X\n" +
        "DTAyMTEwNzExNTcwM1oXDTIyMTEwNzExNTcwM1owFTETMBEGA1UEAxMKRW5kIEVu\n" +
        "dGl0eTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQDVBDfF+uBr5s5jzzDs1njKlZNt\n" +
        "h8hHzEt3ASh67Peos+QrDzgpUyFXT6fdW2h7iPf0ifjM8eW2xa+3EnPjjU5jAgMB\n" +
        "AAGjGzAZMBcGA1UdIAQQMA4wBgYEVR0gADAEBgIqADANBgkqhkiG9w0BAQQFAANB\n" +
        "AFo//WOboCNOCcA1fvcWW9oc4MvV8ZPvFIAbyEbgyFd4id5lGDTRbRPvvNZRvdsN\n" +
        "NM2gXYr+f87NHIXc9EF3pzw=\n" +
        "-----END CERTIFICATE-----";
    private final Pkcs12KeyStore keyStore = mock(Pkcs12KeyStore.class);
    private final HttpClient client = mock(HttpClient.class);
    private final Request.Builder builder = new RequestBuilderImpl(client, null, null);
    private AdorsysSigningHeadersInterceptor adorsysSigningHeadersInterceptor;

    @AfterAll
    static void afterAll() {
        // Re-set configuration properties to defaults
        AdapterConfig.setConfigFile("");
    }

    @Test
    void preHandle_requestSigningEnabled() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        String file = getClass().getResource(File.separator + "adorsys.adapter.config.signing-enabled.properties").getFile();
        AdapterConfig.setConfigFile(file);
        adorsysSigningHeadersInterceptor = new AdorsysSigningHeadersInterceptor(keyStore);

        when(keyStore.getQsealCertificate()).thenReturn(generateCertificate());
        when(keyStore.getQsealPrivateKey()).thenReturn(generatePrivateKey());

        Request.Builder actualBuilder = adorsysSigningHeadersInterceptor.preHandle(builder);

        verify(keyStore, times(2)).getQsealCertificate();
        verify(keyStore, times(1)).getQsealPrivateKey();

        assertThat(actualBuilder.headers())
            .isNotEmpty()
            .containsKeys(DATE, TPP_SIGNATURE_CERTIFICATE, DIGEST, SIGNATURE)
            .hasEntrySatisfying(TPP_SIGNATURE_CERTIFICATE, value ->
                assertThat(value).contains(CERTIFICATE.replace("\n", "")));
    }

    @Test
    void preHandle_requestSigningDisabled() {
        String file = getClass().getResource(File.separator + "adorsys.adapter.config.signing-disabled.properties").getFile();
        AdapterConfig.setConfigFile(file);
        adorsysSigningHeadersInterceptor = new AdorsysSigningHeadersInterceptor(keyStore);

        adorsysSigningHeadersInterceptor.preHandle(builder);

        verifyNoInteractions(keyStore);
    }

    private static X509Certificate generateCertificate() {
        X509Certificate certificate = null;
        CertificateFactory cf = null;

        InputStream is = new ByteArrayInputStream(CERTIFICATE.getBytes(StandardCharsets.UTF_8));

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
