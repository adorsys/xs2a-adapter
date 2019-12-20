package de.adorsys.xs2a.adapter.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public interface PkceOauth2Extension {
    // The client SHOULD create a "code_verifier" with a minimum of 256 bits
    // of entropy.  This can be done by having a suitable random number
    // generator create a 32-octet sequence.  The octet sequence can then be
    // base64url-encoded to produce a 43-octet URL safe string to use as a
    // "code_challenge" that has the required entropy.

    byte[] codeVerifier = random(32);

    static byte[] random(int numBytes) {
        try {
            return SecureRandom.getInstanceStrong().generateSeed(numBytes);
        } catch (NoSuchAlgorithmException e) {
            // Every implementation of the Java platform is required to
            // support at least one strong {@code SecureRandom} implementation.
            throw new RuntimeException(e);
        }
    }

    default byte[] octetSequence() {
        return codeVerifier;
    }

    default String codeVerifier() {
        return base64urlNoPadding(octetSequence());
    }

    static String base64urlNoPadding(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    default String codeChallenge() {
        return base64urlNoPadding(sha256(octetSequence()));
    }

    static byte[] sha256(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            // Every implementation of the Java platform is required to support SHA-256
            throw new RuntimeException(e);
        }
    }
}
