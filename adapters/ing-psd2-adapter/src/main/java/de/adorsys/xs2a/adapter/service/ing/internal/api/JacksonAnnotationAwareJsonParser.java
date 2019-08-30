package de.adorsys.xs2a.adapter.service.ing.internal.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.util.ObjectParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public class JacksonAnnotationAwareJsonParser implements ObjectParser {

    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public <T> T parseAndClose(InputStream in, Charset charset, Class<T> dataClass) throws IOException {
        return objectMapper.readValue(in, dataClass);
    }

    @Override
    public Object parseAndClose(InputStream in, Charset charset, Type dataType) throws IOException {
        return objectMapper.readValue(in, objectMapper.constructType(dataType));
    }

    @Override
    public <T> T parseAndClose(Reader reader, Class<T> dataClass) throws IOException {
        return objectMapper.readValue(reader, dataClass);
    }

    @Override
    public Object parseAndClose(Reader reader, Type dataType) throws IOException {
        return objectMapper.readValue(reader, objectMapper.constructType(dataType));
    }
}
