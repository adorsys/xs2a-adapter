package de.adorsys.xs2a.adapter.impl.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {

    @Test
    void parse() {
        HttpHeaders httpHeaders = HttpHeaders.parse("Content-Disposition: form-data; name=\"xml_sct\"\r\n" +
            "Content-Type: application/xml\r\n" +
            "Content-Length: 1434\r\n\r\n");

        assertThat(httpHeaders.size()).isEqualTo(3);
    }

    @Test
    void parseToleratesLFsInsteadOfCRLFs() {
        HttpHeaders httpHeaders = HttpHeaders.parse("Content-Disposition: form-data; name=\"xml_sct\"\n" +
            "Content-Type: application/xml\n" +
            "Content-Length: 1434\n\n");

        assertThat(httpHeaders.size()).isEqualTo(3);
    }
}
