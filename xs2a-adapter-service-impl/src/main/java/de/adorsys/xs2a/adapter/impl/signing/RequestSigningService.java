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

package de.adorsys.xs2a.adapter.impl.signing;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.impl.signing.header.Digest;
import de.adorsys.xs2a.adapter.impl.signing.header.Signature;
import de.adorsys.xs2a.adapter.impl.signing.header.TppSignatureCertificate;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;

import static de.adorsys.xs2a.adapter.impl.signing.util.Constants.*;

public class RequestSigningService {

    private final Pkcs12KeyStore keyStore;

    public RequestSigningService(Pkcs12KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public Digest buildDigest(String requestBody) {
        return Digest.builder()
                       .requestBody(requestBody)
                       .build();
    }

    public Signature buildSignature(Map<String, String> headersMap) {
        return Signature.builder()
                       .keyId(getKeyId())
                       .headers(headersMap)
                       .privateKey(getPrivateKey())
                       .build();
    }

    private String getKeyId() {
        X509Certificate certificate = getCertificate();
        return CERTIFICATE_SERIAL_NUMBER_ATTRIBUTE
            + EQUALS_SIGN_SEPARATOR
            + certificate.getSerialNumber().toString(16) // toString(16) is used to provide hexadecimal coding as mentioned in specification
            + COMMA_SEPARATOR
            + CERTIFICATION_AUTHORITY_ATTRIBUTE
            + EQUALS_SIGN_SEPARATOR
            + certificate.getIssuerX500Principal()
            .getName()
            .replaceAll(SPACE_SEPARATOR, HEXADECIMAL_SPACE_SEPARATOR);
    }

    private X509Certificate getCertificate() {
        X509Certificate certificate = null;
        try {
            certificate = keyStore.getQsealCertificate();
        } catch (KeyStoreException e) {
            throw new HttpRequestSigningException(e);
        }
        return certificate;
    }

    private PrivateKey getPrivateKey() {
        try {
            return keyStore.getQsealPrivateKey();
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new HttpRequestSigningException(e);
        }
    }

    public TppSignatureCertificate buildTppSignatureCertificate() {
        return TppSignatureCertificate.builder()
                       .publicKeyAsString(getPublicKeyAsString())
                       .build();
    }

    private String getPublicKeyAsString() {
        X509Certificate certificate = getCertificate();

        try {
            return Base64.getEncoder().encodeToString(certificate.getEncoded());
        } catch (CertificateEncodingException e) {
            throw new HttpRequestSigningException(e);
        }
    }
}
