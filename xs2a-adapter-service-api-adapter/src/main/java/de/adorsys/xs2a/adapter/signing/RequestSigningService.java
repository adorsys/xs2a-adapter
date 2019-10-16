package de.adorsys.xs2a.adapter.signing;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.signing.header.Digest;
import de.adorsys.xs2a.adapter.signing.header.Signature;
import de.adorsys.xs2a.adapter.signing.header.TppSignatureCertificate;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;

import static de.adorsys.xs2a.adapter.signing.util.Constants.*;

public class RequestSigningService {

    private final Pkcs12KeyStore keyStore;

    public RequestSigningService(Pkcs12KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public Digest buildDigest(String requestBody) {
        return Digest.builder()
                       .requestBody(requestBody)
                       .build();
    }

    public Signature buildSignature(Map<String, String> headersMap) {
        return Signature.builder()
                       .keyId(getKeyId())
                       .headers(headersMap)
                       .privateKey(getPrivateKey())
                       .build();
    }

    private String getKeyId() {
        X509Certificate certificate = getCertificate();
        return CERTIFICATE_SERIAL_NUMBER_ATTRIBUTE
            + EQUALS_SIGN_SEPARATOR
            + certificate.getSerialNumber().toString(16) // toString(16) is used to provide hexadecimal coding as mentioned in specification
            + COMMA_SEPARATOR
            + CERTIFICATION_AUTHORITY_ATTRIBUTE
            + EQUALS_SIGN_SEPARATOR
            + certificate.getIssuerX500Principal()
            .getName()
            .replaceAll(SPACE_SEPARATOR, HEXADECIMAL_SPACE_SEPARATOR);
    }

    private X509Certificate getCertificate() {
        X509Certificate certificate = null;
        try {
            certificate = keyStore.getQsealCertificate();
        } catch (KeyStoreException e) {
            throw new HttpRequestSigningException(e);
        }
        return certificate;
    }

    private PrivateKey getPrivateKey() {
        try {
            return keyStore.getQsealPrivateKey();
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new HttpRequestSigningException(e);
        }
    }

    public TppSignatureCertificate buildTppSignatureCertificate() {
        return TppSignatureCertificate.builder()
                       .publicKeyAsString(getPublicKeyAsString())
                       .build();
    }

    private String getPublicKeyAsString() {
        X509Certificate certificate = getCertificate();

        try {
            return Base64.getEncoder().encodeToString(certificate.getEncoded());
        } catch (CertificateEncodingException e) {
            throw new HttpRequestSigningException(e);
        }
    }
}
