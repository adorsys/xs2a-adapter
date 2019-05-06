package de.adorsys.xs2a.gateway.signing.service.algorithm;

import de.adorsys.xs2a.gateway.signing.service.signing.RsaSha256SigningService;
import de.adorsys.xs2a.gateway.signing.service.signing.SigningService;

public enum SigningAlgorithm {
    RSA_SHA256("SHA256withRSA", "rsa-sha256", new RsaSha256SigningService());

    private String algorithmName;
    private String httpName;
    private SigningService signingService;

    SigningAlgorithm(String algorithmName, String httpName, SigningService signingService) {
        this.algorithmName = algorithmName;
        this.httpName = httpName;
        this.signingService = signingService;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public String getHttpName() {
        return httpName;
    }

    public SigningService getSigningService() {
        return signingService;
    }
}
