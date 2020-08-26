package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import de.adorsys.xs2a.adapter.api.exception.HttpRequestSigningException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum HashingAlgorithm {
    SHA256("SHA-256", new Sha256HashingService()),
    SHA512("SHA-512", new Sha512HashingService());

    private final String algorithmName;
    private final HashingService hashingService;

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

    public interface HashingService {

        byte[] hash(String data, Charset charset);
    }

    private abstract static class AbstractShaHashingService implements HashingService {

        @SuppressWarnings("java:S4790")
        @Override
        public byte[] hash(String data, Charset charset) {
            try {
                return MessageDigest.getInstance(getAlgorithm().getAlgorithmName())
                    .digest(data.getBytes(charset));
            } catch (NoSuchAlgorithmException e) {
                throw new HttpRequestSigningException("No such hashing algorithm: "
                    + getAlgorithm().getAlgorithmName());
            }
        }

        protected abstract HashingAlgorithm getAlgorithm();
    }

    private static class Sha256HashingService extends AbstractShaHashingService {

        @Override
        protected HashingAlgorithm getAlgorithm() {
            return HashingAlgorithm.SHA256;
        }
    }

    private static class Sha512HashingService extends AbstractShaHashingService {

        @Override
        protected HashingAlgorithm getAlgorithm() {
            return HashingAlgorithm.SHA512;
        }
    }
}
