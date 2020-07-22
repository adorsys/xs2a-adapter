package de.adorsys.xs2a.adapter.signing.header;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TppSignatureCertificateTest {
    private static final String PUBLIC_KEY_AS_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";

    @Test
    void build() {
        TppSignatureCertificate tppSignatureCertificate = TppSignatureCertificate.builder()
            .publicKeyAsString(PUBLIC_KEY_AS_STRING)
            .build();

        assertThat(tppSignatureCertificate.getHeaderName()).isEqualTo(RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
        assertThat(tppSignatureCertificate.getHeaderValue()).isEqualTo(PUBLIC_KEY_AS_STRING);
    }
}
