package de.adorsys.xs2a.gateway.signing.service.hashing;

import de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm;

import static de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm.SHA512;

public class Sha512HashingService extends ShaHashingService {

    @Override
    protected HashingAlgorithm getAlgorithm() {
        return SHA512;
    }
}
