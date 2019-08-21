package de.adorsys.xs2a.adapter.http;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Psd2DateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    private DateTimeFormatter psd2Formatter = new DateTimeFormatterBuilder()
                                                        // date/time
                                                        .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                                                        // offset (hh:mm - "+00:00" when it's zero)
                                                        .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
                                                        // offset (hhmm - "+0000" when it's zero)
                                                        .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
                                                        // offset (hh - "Z" when it's zero)
                                                        .optionalStart().appendOffset("+HH", "Z").optionalEnd()
                                                        // create formatter
                                                        .toFormatter();

    @Override
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String text = parser.getText();

        if (text == null || text.isEmpty()) {
            return null;
        }

        return OffsetDateTime.parse(text, psd2Formatter);
    }
}
