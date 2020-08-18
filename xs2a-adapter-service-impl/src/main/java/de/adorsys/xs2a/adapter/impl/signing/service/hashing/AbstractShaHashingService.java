package de.adorsys.xs2a.adapter.impl.signing.service.hashing;

import de.adorsys.xs2a.adapter.service.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.impl.signing.service.algorithm.HashingAlgorithm;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractShaHashingService implements HashingService {

    @SuppressWarnings("java:S4790")
    @Override
    public byte[] hash(String data, Charset charset) {
        try {
            return MessageDigest.getInstance(getAlgorithm().getAlgorithmName())
                           .digest(data.getBytes(charset));
        } catch (NoSuchAlgorithmException e) {
            throw new HttpRequestSigningException("No such hashing algorithm: " + getAlgorithm().getAlgorithmName());
        }
    }

    protected abstract HashingAlgorithm getAlgorithm();
}
