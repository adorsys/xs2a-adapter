package de.adorsys.xs2a.adapter.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Xs2AHttpLogSanitizerTest {

    public static final String REPLACEMENT = "******";
    private final Xs2aHttpLogSanitizer anonymizer = new Xs2aHttpLogSanitizer();

    @Test
    public void sanitize() {

        String data = "https://xs2a-sndbx.consorsbank.de/v1/consents/a9b2d4e9-91da-4a1b-bf9b-d7619aa67462/authorisations";
        String sanitizedString = anonymizer.sanitize(data);

        assertThat(sanitizedString).isEqualTo("https://xs2a-sndbx.consorsbank.de/v1/consents/******/authorisations");

        data = "https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents/3e91db48-6d2e-44d3-b281-3a650d0292c3/authorisations/e15b3267-572e-400b-b87a-fb242eb52bb5";
        sanitizedString = anonymizer.sanitize(data);

        assertThat(sanitizedString).isEqualTo("https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents/******/authorisations/******");
    }

    @Test
    public void sanitizeSensitiveHeaders() {
        List<String> headers = Arrays.asList("Authorization", "PSU-ID", "PSU-Corporate-ID", "Consent-ID", "X-GTW-IBAN");
        for (String header : headers) {
            String sanitizedHeader = anonymizer.sanitizeHeader(header, "1234567");
            assertThat(sanitizedHeader).isEqualTo(REPLACEMENT);
        }
    }

    @Test
    public void sanitizeGeneralHeader() {
        List<String> headers = Arrays.asList("Content-type", "Accept", "Correlation-ID");
        for (String header : headers) {
            String sanitizedHeader = anonymizer.sanitizeHeader(header, "1234567");
            assertThat(sanitizedHeader).isEqualTo("1234567");
        }
    }

    @Test
    public void sanitizeResponseBody() throws IOException {
        String json = "{" +
                          "\"consentStatus\":\"received\"," +
                          "\"consentId\":\"40b01787-a5eb-48c0-bc38-050b8e657a88\"," +
                          "\"_links\":{" +
                          "\"scaStatus\":{" +
                          "\"href\":\"https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents/40b01787-a5eb-48c0-bc38-050b8e657a88/authorisations/9625c345-c41a-4c69-b9ea-824fa2c69c3a\"" +
                          "}," +
                          "\"startAuthorisationWithEncryptedPsuAuthentication\":{" +
                          "\"href\":\"https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents/40b01787-a5eb-48c0-bc38-050b8e657a88/authorisations/9625c345-c41a-4c69-b9ea-824fa2c69c3a\"" +
                          "}," +
                          "\"self\":{" +
                          "\"href\":\"https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents/40b01787-a5eb-48c0-bc38-050b8e657a88\"" +
                          "}," +
                          "\"aspspCertificates\":{" +
                          "\"href\":\"https://simulator-xs2a.db.com/DE/SB-DB/aspsp-certificates/\"" +
                          "}," +
                          "\"status\":{" +
                          "\"href\":\"https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/consents/40b01787-a5eb-48c0-bc38-050b8e657a88/status\"" +
                          "}" +
                          "  }" +
                          "}";
        String expectedJson = "{" +
                                  "\"consentStatus\":\"******\"," +
                                  "\"consentId\":\"******\"," +
                                  "\"_links\":{" +
                                  "\"scaStatus\":{" +
                                  "\"href\":\"******\"" +
                                  "}," +
                                  "\"startAuthorisationWithEncryptedPsuAuthentication\":{" +
                                  "\"href\":\"******\"" +
                                  "}," +
                                  "\"self\":{" +
                                  "\"href\":\"******\"" +
                                  "}," +
                                  "\"aspspCertificates\":{" +
                                  "\"href\":\"******\"" +
                                  "}," +
                                  "\"status\":{" +
                                  "\"href\":\"******\"" +
                                  "}" +
                                  "}" +
                                  "}";

        ConsentCreationResponse consentCreationResponse = new ObjectMapper().readValue(json, ConsentCreationResponse.class);

        String body = anonymizer.sanitizeResponseBody(consentCreationResponse, ContentType.APPLICATION_JSON);

        assertThat(body).isEqualTo(expectedJson);
    }

    @Test
    public void sanitizeResponseBodyNotJsonContentType() {
        String body = anonymizer.sanitizeResponseBody(new Object(), "application/xml");
        assertThat(body).isEqualTo(REPLACEMENT);
    }

    @Test
    public void sanitizeResponseBodyWithSerializationError() {
        String body = anonymizer.sanitizeResponseBody(Arrays.asList("abc", "123"), "application/json");
        assertThat(body).isEqualTo(REPLACEMENT);
    }
}
