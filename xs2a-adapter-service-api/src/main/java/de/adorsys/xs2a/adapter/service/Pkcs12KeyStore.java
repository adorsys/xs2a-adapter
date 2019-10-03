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
    private static final String DEFAULT_QWAC_ALIAS = "default_qwac";
    private static final String DEFAULT_QSEAL_ALIAS = "default_qseal";
    private static final char[] DEFAULT_PASSWORD = new char[]{};

    private final KeyStore keyStore;
    private final char[] defaultPassword;
    private final String defaultQwacAlias;
    private final String defaultQsealAlias;

    public Pkcs12KeyStore(String filename)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this(filename, DEFAULT_PASSWORD);
    }

    public Pkcs12KeyStore(String filename, char[] defaultPassword)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this(filename, defaultPassword, DEFAULT_QWAC_ALIAS, DEFAULT_QSEAL_ALIAS);
    }

    public Pkcs12KeyStore(String filename, char[] defaultPassword, String defaultQwacAlias, String defaultQsealAlias)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this.keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        this.defaultPassword = defaultPassword;
        this.defaultQwacAlias = defaultQwacAlias;
        this.defaultQsealAlias = defaultQsealAlias;
        FileInputStream inputStream = new FileInputStream(filename);
        keyStore.load(inputStream, defaultPassword);
    }

    public SSLContext getSslContext()
        throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException, KeyManagementException, IOException, CertificateException {

        return getSslContext(defaultQwacAlias);
    }

    public SSLContext getSslContext(String qwacAlias)
        throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyManagementException {

        KeyStore qwacKeyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        qwacKeyStore.load(null, defaultPassword);
        KeyStore.Entry entry = keyStore.getEntry(qwacAlias, new KeyStore.PasswordProtection(defaultPassword));
        qwacKeyStore.setEntry("", entry, new KeyStore.PasswordProtection(defaultPassword));

        SSLContext sslContext = SSLContext.getInstance("TLS");
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(qwacKeyStore, defaultPassword);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        sslContext.init(keyManagers, null, null);
        return sslContext;
    }

    public X509Certificate getQsealCertificate() throws KeyStoreException {
        return getQsealCertificate(defaultQsealAlias);
    }

    public X509Certificate getQsealCertificate(String qsealAlias) throws KeyStoreException {
        return (X509Certificate) keyStore.getCertificate(qsealAlias);
    }

    public PrivateKey getQsealPrivateKey()
        throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

        return getQsealPrivateKey(defaultQsealAlias);
    }

    public PrivateKey getQsealPrivateKey(String qsealAlias)
        throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {

        return (PrivateKey) keyStore.getKey(qsealAlias, defaultPassword);
    }
}
