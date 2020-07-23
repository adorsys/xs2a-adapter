package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.ing.model.IngApplicationTokenResponse;

import javax.security.auth.x500.X500Principal;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class IngClientAuthenticationFactory {
    private final Signature signature;
    private final MessageDigest digest;
    private final String tppSignatureCertificate;
    private final String keyId;

    @SuppressWarnings("java:S4790")
    public IngClientAuthenticationFactory(X509Certificate certificate, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, CertificateEncodingException {
        signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        digest = MessageDigest.getInstance("SHA-256");
        tppSignatureCertificate = base64(certificate.getEncoded());
        keyId = keyId(certificate);
    }

    private String keyId(X509Certificate certificate) {
        return "SN=" + certificate.getSerialNumber().toString(16)
            + ",CA=" + issuerNameRfc2253(certificate);
    }

    private String issuerNameRfc2253(X509Certificate qSealCertificate) {
        X500Principal issuerX500Principal = qSealCertificate.getIssuerX500Principal();
        return issuerX500Principal.getName(X500Principal.RFC2253);
    }

    private String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public IngClientAuthentication newClientAuthenticationForApplicationToken() {
        return new IngClientAuthentication(signature, digest, tppSignatureCertificate, keyId, null);
    }

    public IngClientAuthentication newClientAuthentication(IngApplicationTokenResponse applicationToken) {
        return new IngClientAuthentication(signature, digest, tppSignatureCertificate, applicationToken.getClientId(), applicationToken.getAccessToken());
    }

    public IngClientAuthentication newClientAuthentication(IngApplicationTokenResponse applicationToken, String accessToken) {
        return new IngClientAuthentication(signature, digest, tppSignatureCertificate, applicationToken.getClientId(), accessToken);
    }
}
