/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public interface PkceOauth2Extension {

    static byte[] random(int numBytes) {
        try {
            return SecureRandom.getInstanceStrong().generateSeed(numBytes);
        } catch (NoSuchAlgorithmException e) {
            // Every implementation of the Java platform is required to
            // support at least one strong {@code SecureRandom} implementation.
            throw new Xs2aAdapterException(e);
        }
    }

    default byte[] octetSequence() {
        return StaticCodeVerifier.codeVerifier;
    }

    default String codeVerifier() {
        return base64urlNoPadding(octetSequence());
    }

    static String base64urlNoPadding(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    default String codeChallenge() {
        return base64urlNoPadding(sha256(codeVerifier().getBytes()));
    }

    @SuppressWarnings("java:S4790")
    static byte[] sha256(byte[] bytes) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            // Every implementation of the Java platform is required to support SHA-256
            throw new Xs2aAdapterException(e);
        }
    }

    class StaticCodeVerifier {

        private StaticCodeVerifier() {
        }

        // The client SHOULD create a "code_verifier" with a minimum of 256 bits
        // of entropy.  This can be done by having a suitable random number
        // generator create a 32-octet sequence.  The octet sequence can then be
        // base64url-encoded to produce a 43-octet URL safe string to use as a
        // "code_challenge" that has the required entropy.

        private static final byte[] codeVerifier = random(32);
    }
}
