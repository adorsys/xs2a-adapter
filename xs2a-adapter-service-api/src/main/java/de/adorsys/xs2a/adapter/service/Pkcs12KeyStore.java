package de.adorsys.xs2a.adapter.service;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * A PKCS #12 {@code java.security.KeyStore} that holds certificates and keys
 * used for signing http messages (QSEAL) and client tls authentication (QWAC).
 * By default a key store is expected to have two bags named "default_qwac" and
 * "default_qseal" and an empty password.
 * <p>
 * A key store file may be created using {@code openssl} and {@code keytool}
 * command line tools. First create a p12 file for each certificate/key pair
 * with a specific alias.
 * <pre>
 * openssl pkcs12 -export -out &lt;p12_file&gt; -in &lt;cert_file&gt; -inkey &lt;key_file&gt; -name &lt;alias&gt;
 * </pre>
 * And then combine all p12 files into one.
 * <pre>
 * keytool -importkeystore -srckeystore &lt;src_p12&gt; -destkeystore &lt;dest_p12&gt; -srcstorepass '' -deststorepass ''
 * </pre>
 */
public class Pkcs12KeyStore {
    private static final String KEY_STORE_TYPE = "PKCS12";

    private static final String DEFAULT_QWAC_ALIAS = "default_qwac";
    private static final String DEFAULT_QSEAL_ALIAS = "default_qseal";
    private static final char[] DEFAULT_PASSWORD = new char[]{};

    private final KeyStore keyStore;
    private final char[] password;
    private final String defaultQwacAlias;
    private final String defaultQsealAlias;

    public Pkcs12KeyStore(String filename)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this(filename, DEFAULT_PASSWORD);
    }

    public Pkcs12KeyStore(String filename, char[] password)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this(filename, password, DEFAULT_QWAC_ALIAS, DEFAULT_QSEAL_ALIAS);
    }

    public Pkcs12KeyStore(String filename, char[] password, String defaultQwacAlias, String defaultQsealAlias)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this.keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        this.password = password;
        this.defaultQwacAlias = defaultQwacAlias;
        this.defaultQsealAlias = defaultQsealAlias;
        FileInputStream inputStream = new FileInputStream(filename);
        keyStore.load(inputStream, password);
    }

    public SSLContext getSslContext()
        throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException, KeyManagementException, IOException, CertificateException {

        return getSslContext(defaultQwacAlias);
    }

    public SSLContext getSslContext(String qwacAlias)
        throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyManagementException {

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

        return (PrivateKey) keyStore.getKey(qsealAlias, password);
    }
}
