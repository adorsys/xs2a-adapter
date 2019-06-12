package de.adorsys.xs2a.gateway.signing.storage;

import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import static de.adorsys.xs2a.gateway.signing.util.Constants.*;

public class KeyStorageService {
    private static final KeyStorageService KEY_STORAGE_SERVICE = new KeyStorageService();

    private String privateKeyPath;
    private String privateKeyPassword;
    private String keyAlias;

    private KeyStore keystore;
    private X509Certificate certificate;
    private String publicKeyAsString;
    private PrivateKey privateKey;

    private String keyId;

    public static KeyStorageService getInstance() {
        return KEY_STORAGE_SERVICE;
    }

    private KeyStorageService() {
        initProperties();
        initKey();
        initCertificate();
        initAlias();
    }

    private void initProperties() {
        this.privateKeyPath = System.getProperty(KEY_PATH_SYSTEM_PROPERTY);
        this.privateKeyPassword = System.getProperty(KEYSTORE_PASSWORD_SYSTEM_PROPERTY);
    }

    private void initKey() {
        try {
            keystore = KeyStore.getInstance(System.getProperty(KEYSTORE_TYPE_SYSTEM_PROPERTY));
            keystore.load(new FileInputStream(new File(privateKeyPath)), privateKeyPassword.toCharArray());
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new HttpRequestSigningException("Exception during the private key initialisation: " + e);
        }
    }

    private void initCertificate() {
        try {
            certificate = (X509Certificate) keystore.getCertificate("privatekey");
        } catch (KeyStoreException e) {
            throw new HttpRequestSigningException("Exception during the public key initialisation: " + e);
        }
    }

    private void initAlias() {
        try {
            Enumeration<String> aliases = keystore.aliases();

            if (!aliases.hasMoreElements()) {
                throw new HttpRequestSigningException("Key store is empty");
            }

            String alias = aliases.nextElement();

            if (aliases.hasMoreElements()) {
                throw new HttpRequestSigningException("Key store contains more, than one key");
            }

            this.keyAlias = alias;
        } catch (KeyStoreException e) {
            throw new HttpRequestSigningException("Exception during the getting the aliases from the key store: " + e);
        }

    }

    public String getPublicKeyAsString() {
        if (publicKeyAsString == null) {
            try (StringWriter writer = new StringWriter();
                 JcaPEMWriter pemWriter = new JcaPEMWriter(writer)) {
                pemWriter.writeObject(keystore.getCertificate(keyAlias));
                pemWriter.flush();

                publicKeyAsString = writer.toString()
                                            .replace(BEGIN_CERTIFICATE_LABEL, "")
                                            .replace(END_CERTIFICATE_LABEL, "")
                                            .replaceAll(LINE_BREAK_SEPARATOR, "")
                                            .replaceAll(CARRIAGE_RETURN_SEPARATOR, "");
            } catch (IOException | KeyStoreException e) {
                throw new HttpRequestSigningException("Exception during the getting public key as a string: " + e);
            }
        }

        return publicKeyAsString;
    }

    public String getKeyId() {
        if (keyId == null) {
            keyId = CERTIFICATE_SERIAL_NUMBER_ATTRIBUTE
                            + EQUALS_SIGN_SEPARATOR
                            + certificate.getSerialNumber().toString(16) // toString(16) is used to provide hexadecimal coding as mentioned in specification
                            + COMMA_SEPARATOR
                            + CERTIFICATION_AUTHORITY_ATTRIBUTE
                            + EQUALS_SIGN_SEPARATOR
                            + certificate.getIssuerX500Principal()
                                      .getName()
                                      .replaceAll(SPACE_SEPARATOR, HEXADECIMAL_SPACE_SEPARATOR);
        }

        return keyId;
    }

    public PrivateKey getPrivateKey() {
        if (privateKey == null) {
            try {
                privateKey = (PrivateKey) keystore.getKey(keyAlias, privateKeyPassword.toCharArray());
            } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
                throw new HttpRequestSigningException(String.format("Exception during the key getting by alias [%s]: %s", keyAlias, e));
            }
        }

        return privateKey;
    }
}
