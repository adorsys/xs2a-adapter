package de.adorsys.xs2a.adapter.signing.service.algorithm;

import de.adorsys.xs2a.adapter.signing.service.hashing.HashingService;
import de.adorsys.xs2a.adapter.signing.service.hashing.Sha256HashingService;
import de.adorsys.xs2a.adapter.signing.service.hashing.Sha512HashingService;

public enum HashingAlgorithm {
    SHA256("SHA-256", new Sha256HashingService()),
    SHA512("SHA-512", new Sha512HashingService());

    private String algorithmName;
    private HashingService hashingService;

    HashingAlgorithm(String algorithmName, HashingService hashingService) {
        this.algorithmName = algorithmName;
        this.hashingService = hashingService;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public HashingService getHashingService() {
        return hashingService;
    }
}
