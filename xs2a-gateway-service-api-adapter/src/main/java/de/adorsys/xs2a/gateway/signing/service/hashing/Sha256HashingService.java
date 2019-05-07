package de.adorsys.xs2a.gateway.signing.service.hashing;

import de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm;

import static de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm.SHA256;

public class Sha256HashingService extends ShaHashingService {

    @Override
    protected HashingAlgorithm getAlgorithm() {
        return SHA256;
    }
}
