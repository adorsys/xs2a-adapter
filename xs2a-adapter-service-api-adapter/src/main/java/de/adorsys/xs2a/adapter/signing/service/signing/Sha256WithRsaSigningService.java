package de.adorsys.xs2a.adapter.signing.service.signing;

import de.adorsys.xs2a.adapter.signing.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.signing.service.algorithm.SigningAlgorithm;

import java.nio.charset.Charset;
import java.security.*;

public class Sha256WithRsaSigningService implements SigningService {

    @Override
    public byte[] sign(PrivateKey privateKey, String data, Charset charset) {
        try {
            Signature rsaSha256Signature = Signature.getInstance(getAlgorithm().getAlgorithmName());
            rsaSha256Signature.initSign(privateKey);
            rsaSha256Signature.update(data.getBytes(charset));
            return rsaSha256Signature.sign();
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new HttpRequestSigningException("Exception during the signing algorithm" + getAlgorithm().getAlgorithmName() + " usage: " + e);
        }
    }

    private SigningAlgorithm getAlgorithm() {
        return SigningAlgorithm.SHA256_WITH_RSA;
    }
}
