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

package de.adorsys.xs2a.tpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ErrorResponseDecoder implements ErrorDecoder {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        ResponseHeaders responseHeaders = ResponseHeaders.fromMap(
            response.headers().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().iterator().next()))
        );

        ErrorResponse errorResponse = null;
        try (Reader bodyReader = response.body().asReader()) {
            String responseBody = Util.toString(bodyReader);

            if (StringUtils.isNotBlank(responseBody)) {
                errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ErrorResponseException(response.status(), responseHeaders, errorResponse);
    }
}
