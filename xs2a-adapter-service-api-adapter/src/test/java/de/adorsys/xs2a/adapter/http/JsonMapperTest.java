package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapperTest {
    JsonMapper mapper = new JsonMapper();

    @Test
    void writeValueAsString_throwsException() {
        assertThrows(UncheckedIOException.class, () -> mapper.writeValueAsString(new ByteArrayInputStream(new byte[]{})));
    }

    @Test
    void readValue_string_throwsException() {
        assertThrows(UncheckedIOException.class, () -> mapper.readValue("string", Aspsp.class));
    }

    @Test
    void readValue_inputStream_throwsException() {
        assertThrows(UncheckedIOException.class, () -> mapper.readValue(new ByteArrayInputStream(new byte[]{}), Aspsp.class));
    }
}
