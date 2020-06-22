package de.adorsys.xs2a.adapter.comdirect.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface DateTimeMapper {
    default OffsetDateTime toOffsetDateTime(LocalDateTime dateTime) {
        return ZonedDateTime.of(dateTime, ZoneId.of("Europe/Berlin")).toOffsetDateTime();
    }
}
