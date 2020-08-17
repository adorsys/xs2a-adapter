package de.adorsys.xs2a.adapter.impl.signing.header;

import de.adorsys.xs2a.adapter.impl.signing.util.Constants;

public class TppSignatureCertificate {
    private String headerValue;

    private TppSignatureCertificate(String headerValue) {
        this.headerValue = headerValue;
    }

    public static TppSignatureCertificateBuilder builder() {
        return new TppSignatureCertificateBuilder();
    }

    public String getHeaderName() {
        return Constants.TPP_SIGNATURE_CERTIFICATE_HEADER_NAME;
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
