package de.adorsys.xs2a.gateway.signing.service.algorithm;

import de.adorsys.xs2a.gateway.signing.service.encoding.Base64EncodingService;
import de.adorsys.xs2a.gateway.signing.service.encoding.EncodingService;

public enum EncodingAlgorithm {
    BASE64("BASE64", new Base64EncodingService());

    private String algorithmName;
    private EncodingService encodingService;

    EncodingAlgorithm(String algorithmName, EncodingService encodingService) {
        this.algorithmName = algorithmName;
        this.encodingService = encodingService;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public EncodingService getEncodingService() {
        return encodingService;
    }
}
