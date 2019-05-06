package de.adorsys.xs2a.gateway.signing.service.hashing;

import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm.SHA256;

public class Sha256HashingService implements HashingService {

    @Override
    public byte[] hash(String data, Charset charset) {
        try {
            return MessageDigest.getInstance(getAlgorithm().getAlgorithmName())
                           .digest(data.getBytes(charset));
        } catch (NoSuchAlgorithmException e) {
            throw new HttpRequestSigningException("No such hashing algorithm: " + getAlgorithm().getAlgorithmName());
        }
    }

    private HashingAlgorithm getAlgorithm() {
        return SHA256;
    }
}
