package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.mock.http.MockHttpOutputMessage;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PeriodicPaymentInitiationMultipartBodyHttpMessageConverterTest {

    @Test
    void write() throws IOException {
        PeriodicPaymentInitiationMultipartBodyHttpMessageConverter converter =
            new PeriodicPaymentInitiationMultipartBodyHttpMessageConverter(WebMvcConfig.newConfiguredObjectMapper()) {
                @Override
                protected byte[] generateMultipartBoundary() {
                    return "r3kG3pY8rRua0pHMuWCLa2TESC--Kne".getBytes();
                }
            };
        PeriodicPaymentInitiationMultipartBody body = new PeriodicPaymentInitiationMultipartBody();
        body.setXml_sct("<xml></xml>");
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json =
            new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        json.setDayOfExecution(DayOfExecution._2);
        json.setFrequency(FrequencyCode.QUARTERLY);
        json.setStartDate(LocalDate.of(2020, 1, 1));
        json.setEndDate(LocalDate.of(2021, 1, 1));
        json.setExecutionRule(ExecutionRule.PRECEDING);
        body.setJson_standingorderType(json);
        MockHttpOutputMessage out = new MockHttpOutputMessage();

        converter.write(body, null, out);

        assertThat(out.getBodyAsString())
            .isEqualTo("--r3kG3pY8rRua0pHMuWCLa2TESC--Kne\r\n" +
                "Content-Disposition: form-data; name=\"xml_sct\"\r\n" +
                "Content-Type: application/xml\r\n" +
                "Content-Length: 11\r\n" +
                "\r\n" +
                "<xml></xml>\r\n" +
                "--r3kG3pY8rRua0pHMuWCLa2TESC--Kne\r\n" +
                "Content-Disposition: form-data; name=\"json_standingorderType\"\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                "{\n" +
                "  \"startDate\" : \"2020-01-01\",\n" +
                "  \"endDate\" : \"2021-01-01\",\n" +
                "  \"executionRule\" : \"preceding\",\n" +
                "  \"frequency\" : \"Quarterly\",\n" +
                "  \"dayOfExecution\" : \"2\"\n" +
                "}\r\n" +
                "--r3kG3pY8rRua0pHMuWCLa2TESC--Kne--\r\n");
    }
}
