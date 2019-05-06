package de.adorsys.xs2a.gateway.signing;

import de.adorsys.xs2a.gateway.signing.header.Digest;
import de.adorsys.xs2a.gateway.signing.header.Signature;
import de.adorsys.xs2a.gateway.signing.header.TppSignatureCertificate;
import de.adorsys.xs2a.gateway.signing.storage.KeyStorageService;

import java.util.Map;

public class RequestSigningService {
    private final KeyStorageService keyStorageService = KeyStorageService.getInstance();

    public Digest buildDigest(String requestBody) {
        return Digest.builder()
                       .requestBody(requestBody)
                       .build();
    }

    public Signature buildSignature(Map<String, String> headersMap) {
        return Signature.builder()
                       .keyId(keyStorageService.getKeyId())
                       .headers(headersMap)
                       .privateKey(keyStorageService.getPrivateKey())
                       .build();
    }

    public TppSignatureCertificate buildTppSignatureCertificate() {
        return TppSignatureCertificate.builder()
                       .publicKeyAsString(keyStorageService.getPublicKeyAsString())
                       .build();
    }
}
