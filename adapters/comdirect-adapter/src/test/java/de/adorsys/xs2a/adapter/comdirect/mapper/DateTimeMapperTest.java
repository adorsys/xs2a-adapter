package de.adorsys.xs2a.adapter.comdirect.mapper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeMapperTest {

    private static final DateTimeMapper dateTimeMapper = new TestDateTimeMapper();

    @Test
    void toOffsetDateTime_shouldReturnNull() {
        OffsetDateTime actual = dateTimeMapper.toOffsetDateTime(null);

        assertThat(actual)
            .isNull();
    }

    @Test
    void toOffsetDateTime() {
        LocalDateTime benchmark = LocalDateTime.now();
        OffsetDateTime actual = dateTimeMapper.toOffsetDateTime(benchmark);

        assertThat(actual)
        .isNotNull()
        .matches(offsetDateTime ->
            benchmark.getYear() == offsetDateTime.getYear()
            && benchmark.getMonthValue() == offsetDateTime.getMonthValue()
            && benchmark.getDayOfMonth() == offsetDateTime.getDayOfMonth());
    }

    private static class TestDateTimeMapper implements DateTimeMapper { }
}
