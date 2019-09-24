package de.adorsys.xs2a.adapter.service;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Pkcs12KeyStore {
    private static final String KEY_STORE_TYPE = "PKCS12";

    // todo https://git.adorsys.de/xs2a-gateway/xs2a-gateway/issues/327
    private static final String QWAC_ALIAS = "example_eidas_client_tls";
    private static final String QSEAL_ALIAS = "example_eidas_client_signing";
    private static final char[] PASSWORD = new char[]{};

    private final KeyStore keyStore;
    private final char[] password;
    private final String qwacAlias;
    private final String qsealAlias;

    public Pkcs12KeyStore(String filename) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        this(filename, PASSWORD);
    }

    public Pkcs12KeyStore(String filename, char[] password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        this(filename, password, QWAC_ALIAS, QSEAL_ALIAS);
    }

    public Pkcs12KeyStore(String filename, char[] password, String qwacAlias, String qsealAlias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        this.keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        this.password = password;
        this.qwacAlias = qwacAlias;
        this.qsealAlias = qsealAlias;
        FileInputStream inputStream = new FileInputStream(filename);
        keyStore.load(inputStream, password);
    }

    public SSLContext getSslContext() throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException, KeyManagementException, IOException, CertificateException {
        KeyStore qwacKeyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        qwacKeyStore.load(null, password);
        KeyStore.Entry entry = keyStore.getEntry(qwacAlias, new KeyStore.PasswordProtection(password));
        qwacKeyStore.setEntry("", entry, new KeyStore.PasswordProtection(password));

        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(qwacKeyStore, password);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        sslContext.init(keyManagers, null, null);
        return sslContext;
    }

    public X509Certificate getQsealCertificate() throws KeyStoreException {
        return (X509Certificate) keyStore.getCertificate(qsealAlias);
    }

    public PrivateKey getQsealPrivateKey() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey(qsealAlias, password);
    }
}
