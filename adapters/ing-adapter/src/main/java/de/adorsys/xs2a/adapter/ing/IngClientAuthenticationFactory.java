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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.ing.model.IngApplicationTokenResponse;

import javax.security.auth.x500.X500Principal;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class IngClientAuthenticationFactory {
    private final Signature signature;
    private final MessageDigest digest;
    private final String tppSignatureCertificate;
    private final String keyId;

    @SuppressWarnings("java:S4790")
    public IngClientAuthenticationFactory(X509Certificate certificate, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, CertificateEncodingException {
        signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        digest = MessageDigest.getInstance("SHA-256");
        tppSignatureCertificate = base64(certificate.getEncoded());
        keyId = keyId(certificate);
    }

    public static IngClientAuthenticationFactory getClientAuthenticationFactory(Pkcs12KeyStore keyStore) {
        String qsealAlias = AdapterConfig.readProperty("ing.qseal.alias");
        try {
            X509Certificate qsealCertificate = keyStore.getQsealCertificate(qsealAlias);
            PrivateKey qsealPrivateKey = keyStore.getQsealPrivateKey(qsealAlias);
            return new IngClientAuthenticationFactory(qsealCertificate, qsealPrivateKey);
        } catch (GeneralSecurityException e) {
            throw new Xs2aAdapterException(e);
        }
    }

    private String keyId(X509Certificate certificate) {
        return "SN=" + certificate.getSerialNumber().toString(16)
            + ",CA=" + issuerNameRfc2253(certificate);
    }

    private String issuerNameRfc2253(X509Certificate qSealCertificate) {
        X500Principal issuerX500Principal = qSealCertificate.getIssuerX500Principal();
        return issuerX500Principal.getName(X500Principal.RFC2253);
    }

    private String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public IngClientAuthentication newClientAuthenticationForApplicationToken() {
        return new IngClientAuthentication(signature, digest, tppSignatureCertificate, keyId, null);
    }

    public IngClientAuthentication newClientAuthentication(IngApplicationTokenResponse applicationToken) {
        return new IngClientAuthentication(signature, digest, tppSignatureCertificate, applicationToken.getClientId(), applicationToken.getAccessToken());
    }

    public IngClientAuthentication newClientAuthentication(IngApplicationTokenResponse applicationToken, String accessToken) {
        return new IngClientAuthentication(signature, digest, tppSignatureCertificate, applicationToken.getClientId(), accessToken);
    }
}
