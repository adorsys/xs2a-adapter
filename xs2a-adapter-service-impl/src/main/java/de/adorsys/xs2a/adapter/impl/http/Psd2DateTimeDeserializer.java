/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.impl.http;

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
