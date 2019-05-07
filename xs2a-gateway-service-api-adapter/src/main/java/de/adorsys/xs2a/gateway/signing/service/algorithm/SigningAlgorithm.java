package de.adorsys.xs2a.gateway.signing.service.algorithm;

import de.adorsys.xs2a.gateway.signing.service.signing.Sha256WithRsaSigningService;
import de.adorsys.xs2a.gateway.signing.service.signing.SigningService;

public enum SigningAlgorithm {
    SHA256_WITH_RSA("SHA256withRSA", "SHA256withRSA", new Sha256WithRsaSigningService());

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
