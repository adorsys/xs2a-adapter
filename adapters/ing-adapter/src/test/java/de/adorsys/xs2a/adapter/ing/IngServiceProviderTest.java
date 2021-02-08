package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IngServiceProviderTest {

    private static final String B_64_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n" +
        "MIIBLTCB2KADAgECAgEDMA0GCSqGSIb3DQEBBAUAMA0xCzAJBgNVBAMTAkNBMB4X\n" +
        "DTAyMTEwNzExNTcwM1oXDTIyMTEwNzExNTcwM1owFTETMBEGA1UEAxMKRW5kIEVu\n" +
        "dGl0eTBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQDVBDfF+uBr5s5jzzDs1njKlZNt\n" +
        "h8hHzEt3ASh67Peos+QrDzgpUyFXT6fdW2h7iPf0ifjM8eW2xa+3EnPjjU5jAgMB\n" +
        "AAGjGzAZMBcGA1UdIAQQMA4wBgYEVR0gADAEBgIqADANBgkqhkiG9w0BAQQFAANB\n" +
        "AFo//WOboCNOCcA1fvcWW9oc4MvV8ZPvFIAbyEbgyFd4id5lGDTRbRPvvNZRvdsN\n" +
        "NM2gXYr+f87NHIXc9EF3pzw=\n" +
        "-----END CERTIFICATE-----";

    private IngServiceProvider serviceProvider;
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final Pkcs12KeyStore keyStore = mock(Pkcs12KeyStore.class);
    private final X509Certificate certificate = generateCertificate();
    private final PrivateKey privateKey = generatePrivateKey();
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        serviceProvider = new IngServiceProvider();
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);
        when(keyStore.getQsealCertificate(any())).thenReturn(certificate);
        when(keyStore.getQsealPrivateKey(any())).thenReturn(privateKey);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = serviceProvider.getAccountInformationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isInstanceOf(IngAccountInformationService.class);
    }

    @Test
    void getOauth2Service() {
        Oauth2Service actualService
            = serviceProvider.getOauth2Service(aspsp, httpClientFactory);

        assertThat(actualService)
            .isInstanceOf(IngAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = serviceProvider.getPaymentInitiationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isInstanceOf(IngPaymentInitiationService.class);
    }

    private static X509Certificate generateCertificate() {
        X509Certificate certificate = null;
        CertificateFactory cf = null;

        InputStream is = new ByteArrayInputStream(B_64_CERTIFICATE.getBytes(StandardCharsets.UTF_8));

        try {
            cf = CertificateFactory.getInstance("X.509");
            certificate = (X509Certificate) cf.generateCertificate(is);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

        return certificate;
    }

    private PrivateKey generatePrivateKey() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        KeyPair keyPair = generator.genKeyPair();

        return keyPair.getPrivate();
    }
}
