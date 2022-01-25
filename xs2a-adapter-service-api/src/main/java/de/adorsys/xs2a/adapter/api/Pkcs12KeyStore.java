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

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static final String ORGANIZATION_IDENTIFIER_ATTRIBUTE = "OID.2.5.4.97";

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

        this(new FileInputStream(filename), password, defaultQwacAlias, defaultQsealAlias);
    }

    public Pkcs12KeyStore(InputStream keyStore, char[] password, String defaultQwacAlias, String defaultQsealAlias)
        throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {

        this.keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        this.keyStore.load(Objects.requireNonNull(keyStore), password);
        this.password = password;
        this.defaultQwacAlias = defaultQwacAlias;
        this.defaultQsealAlias = defaultQsealAlias;
    }

    public Pkcs12KeyStore(InputStream keyStore)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {

        this(keyStore, DEFAULT_PASSWORD, DEFAULT_QWAC_ALIAS, DEFAULT_QSEAL_ALIAS);
    }

    public SSLContext getSslContext()
        throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException, KeyManagementException, IOException, CertificateException {

        return getSslContext(null);
    }

    public SSLContext getSslContext(String qwacAlias)
        throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyManagementException {

        if (qwacAlias == null) {
            qwacAlias = defaultQwacAlias;
        }

        KeyStore qwacKeyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        qwacKeyStore.load(null, password);
        KeyStore.Entry entry = keyStore.getEntry(qwacAlias, new KeyStore.PasswordProtection(password));
        qwacKeyStore.setEntry("", entry, new KeyStore.PasswordProtection(password));

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(qwacKeyStore, password);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        sslContext.init(keyManagers, null, null);
        return sslContext;
    }

    public X509Certificate getQsealCertificate() throws KeyStoreException {
        return getQsealCertificate(null);
    }

    public X509Certificate getQsealCertificate(String qsealAlias) throws KeyStoreException {
        if (qsealAlias == null) {
            qsealAlias = defaultQsealAlias;
        }
        return (X509Certificate) keyStore.getCertificate(qsealAlias);
    }

    public PrivateKey getQsealPrivateKey()
        throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

        return getQsealPrivateKey(null);
    }

    public PrivateKey getQsealPrivateKey(String qsealAlias)
        throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {

        if (qsealAlias == null) {
            qsealAlias = defaultQsealAlias;
        }

        return (PrivateKey) keyStore.getKey(qsealAlias, password);
    }

    public String getOrganizationIdentifier() throws KeyStoreException {
        return getOrganizationIdentifier(null);
    }

    public String getOrganizationIdentifier(String qsealAlias) throws KeyStoreException {
        String name = getQsealCertificate(qsealAlias)
                          .getSubjectX500Principal()
                          .getName(X500Principal.RFC1779);

        try {
            return new LdapName(name).getRdns().stream()
                       .filter(rdn -> ORGANIZATION_IDENTIFIER_ATTRIBUTE.equals(rdn.getType()))
                       .map(rdn -> rdn.getValue().toString())
                       .collect(Collectors.joining());
        } catch (InvalidNameException e) {
            throw new Xs2aAdapterException(e);
        }
    }
}
