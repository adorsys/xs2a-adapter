package de.adorsys.xs2a.adapter.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Psd2DateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    private static final String DATE_TIME_PATTERN_LOCAL = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String ZONE_PART = "Z";
    private static final String OFFSET_PART = "XXX";

    private DateTimeFormatter psd2Formatter = new DateTimeFormatterBuilder()
                                                  .append(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_LOCAL))
                                                  .appendOptional(DateTimeFormatter.ofPattern(ZONE_PART))
                                                  .appendOptional(DateTimeFormatter.ofPattern(OFFSET_PART))
                                                  .toFormatter();

    @Override
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return OffsetDateTime.parse(parser.getText(), psd2Formatter);
    }
}
