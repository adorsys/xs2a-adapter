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

package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import de.adorsys.xs2a.adapter.api.exception.HttpRequestSigningException;

import java.nio.charset.Charset;
import java.security.*;

public enum SigningAlgorithm {
    SHA256_WITH_RSA("SHA256withRSA", new Sha256WithRsaSigningService());

    private final String algorithmName;
    private final SigningService signingService;

    SigningAlgorithm(String algorithmName, SigningService signingService) {
        this.algorithmName = algorithmName;
        this.signingService = signingService;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public SigningService getSigningService() {
        return signingService;
    }

    public interface SigningService {

        byte[] sign(PrivateKey privateKey, String data, Charset charset);
    }

    private static class Sha256WithRsaSigningService implements SigningService {

        @Override
        public byte[] sign(PrivateKey privateKey, String data, Charset charset) {
            try {
                Signature rsaSha256Signature = Signature.getInstance(getAlgorithm().getAlgorithmName());
                rsaSha256Signature.initSign(privateKey);
                rsaSha256Signature.update(data.getBytes(charset));
                return rsaSha256Signature.sign();
            } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
                throw new HttpRequestSigningException("Exception during the signing algorithm"
                    + getAlgorithm().getAlgorithmName() + " usage: " + e);
            }
        }

        private SigningAlgorithm getAlgorithm() {
            return SigningAlgorithm.SHA256_WITH_RSA;
        }
    }
}
