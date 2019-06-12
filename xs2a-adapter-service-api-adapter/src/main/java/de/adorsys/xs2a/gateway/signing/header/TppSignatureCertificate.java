package de.adorsys.xs2a.gateway.signing.header;

import static de.adorsys.xs2a.gateway.signing.util.Constants.TPP_SIGNATURE_CERTIFICATE_HEADER_NAME;

public class TppSignatureCertificate {
    private String headerValue;

    private TppSignatureCertificate(String headerValue) {
        this.headerValue = headerValue;
    }

    public static TppSignatureCertificateBuilder builder() {
        return new TppSignatureCertificateBuilder();
    }

    public String getHeaderName() {
        return TPP_SIGNATURE_CERTIFICATE_HEADER_NAME;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public static final class TppSignatureCertificateBuilder {
        private String publicKeyAsString;

        private TppSignatureCertificateBuilder() {
        }

        public TppSignatureCertificateBuilder publicKeyAsString(String publicKeyAsString) {
            this.publicKeyAsString = publicKeyAsString;
            return this;
        }

        public TppSignatureCertificate build() {
            return new TppSignatureCertificate(publicKeyAsString);
        }
    }
}
