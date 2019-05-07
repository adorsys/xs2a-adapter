package de.adorsys.xs2a.gateway.signing.service.algorithm;

import de.adorsys.xs2a.gateway.signing.service.signing.Sha256WithRsaSigningService;
import de.adorsys.xs2a.gateway.signing.service.signing.SigningService;

public enum SigningAlgorithm {
    SHA256_WITH_RSA("SHA256withRSA", new Sha256WithRsaSigningService());

    private String algorithmName;
    private SigningService signingService;

    SigningAlgorithm(String algorithmName, SigningService signingService) {
        this.algorithmName = algorithmName;
        this.signingService = signingService;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public SigningService getSigningService() {
        return signingService;
    }
}
