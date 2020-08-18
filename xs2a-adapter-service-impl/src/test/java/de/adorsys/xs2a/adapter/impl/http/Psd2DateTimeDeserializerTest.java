package de.adorsys.xs2a.adapter.impl.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class Psd2DateTimeDeserializerTest {

    Psd2DateTimeDeserializer deserializer = new Psd2DateTimeDeserializer();

    @Test
    void deserialize() throws IOException {
        OffsetDateTime time = deserializer.deserialize(new JsonParserDelegate(mock(JsonParser.class)), null);

        assertThat(time).isNull();
    }
}
