package de.adorsys.xs2a.gateway.signing.header;

import de.adorsys.xs2a.gateway.service.RequestHeaders;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TppSignatureCertificateTest {
    private static final String PUBLIC_KEY_AS_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";

    @Test
    public void build() {
        TppSignatureCertificate tppSignatureCertificate = TppSignatureCertificate.builder()
                                                .publicKeyAsString(PUBLIC_KEY_AS_STRING)
                                                .build();

        assertThat(tppSignatureCertificate.getHeaderName()).isEqualTo(RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
        assertThat(tppSignatureCertificate.getHeaderValue()).isEqualTo(PUBLIC_KEY_AS_STRING);
    }
}