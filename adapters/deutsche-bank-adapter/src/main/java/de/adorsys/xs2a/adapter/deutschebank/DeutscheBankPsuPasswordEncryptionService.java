package de.adorsys.xs2a.adapter.deutschebank;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64;
import de.adorsys.xs2a.adapter.service.PsuPasswordEncryptionService;
import de.adorsys.xs2a.adapter.service.exception.PsuPasswordEncodingException;
import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Deutsche bank password encryption flow is based on JWE (RFC7516 - https://tools.ietf.org/html/rfc7516)
 */
// TODO adjust this logic on the additional information from Deutsche bank about the certificates API
public class DeutscheBankPsuPasswordEncryptionService implements PsuPasswordEncryptionService {
    private static final String URL_TO_CERTIFICATE = "https://xs2a.db.com/pb/aspsp-certificates/tpp-pb-password_cert.pem";
    private static final String DEFAULT_EXCEPTION_MESSAGE = "Exception during Deutsche bank adapter PSU password encryption";

    private static DeutscheBankPsuPasswordEncryptionService encryptionService;

    private JWEHeader jweHeader;
    private JWEEncrypter jweEncrypter;

    public static DeutscheBankPsuPasswordEncryptionService getInstance() {
        if (encryptionService == null) {
            encryptionService = new DeutscheBankPsuPasswordEncryptionService();
        }

        return encryptionService;
    }

    private DeutscheBankPsuPasswordEncryptionService() {
        init();
    }

    @Override
    public String encrypt(String password) {
        JWEObject jweObject = new JWEObject(jweHeader, new Payload(password));

        try {
            jweObject.encrypt(jweEncrypter);
        } catch (JOSEException e) {
            throw new PsuPasswordEncodingException(DEFAULT_EXCEPTION_MESSAGE, e);
        }

        return jweObject.serialize();
    }

    private void init() {
        CertificateFactory certificateFactory = new CertificateFactory();

        try {
            URI certificateUri = new URI(URL_TO_CERTIFICATE);

            // Warning for unchecked assignment can be ignored,
            // as under the hood CertificateFactory#engineGenerateCertificates returns ArrayList<java.security.cert.Certificate>,
            // even though java.util.Collection is mentioned as a return type.
            // See org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory#engineGenerateCertificates implementation for further details.
            @SuppressWarnings("unchecked")
            Collection<Certificate> certificates = certificateFactory.engineGenerateCertificates(certificateUri.toURL().openStream());

            if (certificates.isEmpty()) {
                throw new PsuPasswordEncodingException("No certificates have been provided by bank for PSU password encryption");
            }

            List<X509Certificate> x509Certificates = toX509Certificates(certificates);

            List<Base64> x509CertificateChainEncoded = x509Certificates.stream()
                                                           .map(this::toBase64)
                                                           .collect(Collectors.toList());

            jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                            .x509CertURL(certificateUri)
                            .x509CertChain(x509CertificateChainEncoded)
                            .build();

            jweEncrypter = new RSAEncrypter(RSAKey.parse(getBankCertificate(x509Certificates)));
        } catch (IOException | CertificateException | URISyntaxException | JOSEException e) {
            throw new PsuPasswordEncodingException(DEFAULT_EXCEPTION_MESSAGE, e);
        }
    }

    private List<X509Certificate> toX509Certificates(Collection<Certificate> certificates) {
        return certificates.stream()
                   .map(certificate -> {
                       if (!(certificate instanceof X509Certificate)) {
                           throw new PsuPasswordEncodingException("Certificate provided by bank is not a X509 type");
                       }
                       return (X509Certificate) certificate;
                   })
                   .collect(Collectors.toList());
    }

    private Base64 toBase64(X509Certificate certificate) {
        try {
            return Base64.encode(certificate.getEncoded());
        } catch (CertificateEncodingException e) {
            throw new PsuPasswordEncodingException(DEFAULT_EXCEPTION_MESSAGE, e);
        }
    }

    private X509Certificate getBankCertificate(List<X509Certificate> certificates) {
        return certificates.get(0);
    }
}
