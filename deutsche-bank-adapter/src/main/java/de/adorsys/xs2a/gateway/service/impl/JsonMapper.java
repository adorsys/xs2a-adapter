package de.adorsys.xs2a.gateway.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class JsonMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    public <T> T readValue(InputStream inputStream, Class<T> klass) {
        try {
            return objectMapper.readValue(inputStream, klass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public <T> T convertValue(Object value, Class<T> klass) {
        return objectMapper.convertValue(value, klass);
    }
}
