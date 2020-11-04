package de.adorsys.xs2a.adapter.crealogix;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

class CrealogixAuthorisationTokenTest {

    @Test
    void encode() throws IOException {
        CrealogixAuthorisationToken expected = new CrealogixAuthorisationToken("tpp", "psd2Authorisation");

        String base64 = expected.encode();

        byte[] tokenBytes = Base64.getDecoder().decode(base64);

        CrealogixAuthorisationToken actual = new ObjectMapper().readValue(tokenBytes, CrealogixAuthorisationToken.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void decode() throws IOException {
        String encodedToken = "eyJ0cHBUb2tlbiI6InRwcCIsICJwc2QyQXV0aG9yaXNhdGlvblRva2VuIjoicHNkMiJ9";
        CrealogixAuthorisationToken token = CrealogixAuthorisationToken.decode(encodedToken);

        assertThat(token.getTppToken()).isEqualTo("tpp");
        assertThat(token.getPsd2AuthorisationToken()).isEqualTo("psd2");
    }
}
