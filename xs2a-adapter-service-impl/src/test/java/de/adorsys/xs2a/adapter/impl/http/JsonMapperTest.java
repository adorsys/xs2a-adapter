package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonMapperTest {
    JsonMapper mapper = new JacksonObjectMapper();

    @Test
    void writeValueAsString_throwsException() {
        ByteArrayInputStream value = new ByteArrayInputStream(new byte[]{});

        assertThrows(UncheckedIOException.class, () -> mapper.writeValueAsString(value));
    }

    @Test
    void readValue_string_throwsException() {
        assertThrows(UncheckedIOException.class, () -> mapper.readValue("string", Aspsp.class));
    }

    @Test
    void readValue_inputStream_throwsException() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{});
        assertThrows(UncheckedIOException.class, () -> mapper.readValue(inputStream, Aspsp.class));
    }
}
