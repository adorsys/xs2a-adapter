package de.adorsys.xs2a.adapter.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<String> headers = Arrays.asList("Authorization", "PSU-ID", "PSU-Corporate-ID", "Consent-ID", "X-GTW-IBAN",
            "Location");
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

        ConsentsResponse201 consentCreationResponse = new ObjectMapper().readValue(json, ConsentsResponse201.class);

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
        assertThat(body).isEqualTo("[\"" + REPLACEMENT + "\",\"" + REPLACEMENT + "\"]");
    }

    @Test
    public void sanitizeResponseBodyAsString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        String body = anonymizer.sanitizeResponseBody(mapper.writeValueAsString(map), "application/json");
        assertThat(body).isEqualTo("{\"key\":\"" + REPLACEMENT + "\"}");
    }

    @Test
    void sanitizeRequestBody() throws UnsupportedEncodingException {
        String json = "{" +
                          "\"access\":{" +
                          "\"balances\":[" +
                          "{" +
                          "\"iban\":\"\"," +
                          "\"currency\":\"EUR\"" +
                          "}" +
                          "]," +
                          "\"transactions\":[" +
                          "{" +
                          "\"iban\":\"DE82500105176963379138\"," +
                          "\"currency\":\"EUR\"" +
                          "}" +
                          "]," +
                          "\"accounts\":[" +
                          "{" +
                          "\"iban\":\"DE82500105176963379138\"," +
                          "\"currency\":\"EUR\"" +
                          "}" +
                          "]" +
                          "}" +
                          "}";

        String expectedSanitizedJson = "{" +
                                           "\"access\":{" +
                                           "\"balances\":[" +
                                           "{" +
                                           "\"iban\":\"******\"," +
                                           "\"currency\":\"******\"" +
                                           "}" +
                                           "]," +
                                           "\"transactions\":[" +
                                           "{" +
                                           "\"iban\":\"******\"," +
                                           "\"currency\":\"******\"" +
                                           "}" +
                                           "]," +
                                           "\"accounts\":[" +
                                           "{" +
                                           "\"iban\":\"******\"," +
                                           "\"currency\":\"******\"" +
                                           "}" +
                                           "]" +
                                           "}" +
                                           "}";

        String actualSanitizedJson = anonymizer.sanitizeRequestBody(new StringEntity(json), "application/json");

        assertThat(actualSanitizedJson).isEqualTo(expectedSanitizedJson);
    }

    @Test
    void sanitizeRequestBodyWithNonSanitizedProperties() throws UnsupportedEncodingException {
        String json = "{" +
                          "\"access\":{" +
                          "\"balances\":[" +
                          "{" +
                          "\"iban\":\"\"," +
                          "\"currency\":\"EUR\"" +
                          "}" +
                          "]," +
                          "\"transactions\":[" +
                          "{" +
                          "\"iban\":\"DE82500105176963379138\"," +
                          "\"currency\":\"EUR\"" +
                          "}" +
                          "]," +
                          "\"accounts\":[" +
                          "{" +
                          "\"iban\":\"DE82500105176963379138\"," +
                          "\"currency\":\"EUR\"" +
                          "}" +
                          "]" +
                          "}," +
                          "\"combinedServiceIndicator\":\"false\"," +
                          "\"recurringIndicator\":\"true\"," +
                          "\"validUntil\":\"01-01-2020\"," +
                          "\"frequencyPerDay\":\"4\"" +
                          "}";

        String expectedSanitizedJson = "{" +
                                           "\"access\":{" +
                                           "\"balances\":[" +
                                           "{" +
                                           "\"iban\":\"******\"," +
                                           "\"currency\":\"******\"" +
                                           "}" +
                                           "]," +
                                           "\"transactions\":[" +
                                           "{" +
                                           "\"iban\":\"******\"," +
                                           "\"currency\":\"******\"" +
                                           "}" +
                                           "]," +
                                           "\"accounts\":[" +
                                           "{" +
                                           "\"iban\":\"******\"," +
                                           "\"currency\":\"******\"" +
                                           "}" +
                                           "]" +
                                           "}," +
                                           "\"combinedServiceIndicator\":\"false\"," +
                                           "\"recurringIndicator\":\"true\"," +
                                           "\"validUntil\":\"01-01-2020\"," +
                                           "\"frequencyPerDay\":\"4\"" +
                                           "}";

        String actualSanitizedJson = anonymizer.sanitizeRequestBody(new StringEntity(json), "application/json");

        assertThat(actualSanitizedJson).isEqualTo(expectedSanitizedJson);
    }

    @Test
    void sanitizeRequestBodyNotJsonContentType() throws UnsupportedEncodingException {
        String actualSanitizedJson = anonymizer.sanitizeRequestBody(new StringEntity("<xml>"), "application/xml");

        assertThat(actualSanitizedJson).isEqualTo(REPLACEMENT);
    }

    @Test
    void sanitizeRequestBodyEmptyBody() throws UnsupportedEncodingException {
        String actualSanitizedJson = anonymizer.sanitizeRequestBody(new StringEntity(""), "application/json");

        assertThat(actualSanitizedJson).isEqualTo(REPLACEMENT);
    }
}
