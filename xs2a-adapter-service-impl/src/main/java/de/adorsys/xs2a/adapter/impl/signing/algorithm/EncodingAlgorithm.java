package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import java.util.Base64;

public enum EncodingAlgorithm {
    BASE64("BASE64", new Base64EncodingService());

    private final String algorithmName;
    private final EncodingService encodingService;

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

    public interface EncodingService {

        String encode(byte[] data);
    }

    private static class Base64EncodingService implements EncodingService {

        @Override
        public String encode(byte[] data) {
            return Base64.getEncoder().encodeToString(data);
        }
    }
}
