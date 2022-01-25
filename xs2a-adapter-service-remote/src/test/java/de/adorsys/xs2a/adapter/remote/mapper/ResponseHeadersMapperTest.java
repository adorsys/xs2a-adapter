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

package de.adorsys.xs2a.adapter.remote.mapper;

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;

import java.util.Collections;

import static de.adorsys.xs2a.adapter.api.ResponseHeaders.*;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeadersMapperTest {

    @Test
    void getHeaders() {
        ResponseHeadersMapper mapper = Mappers.getMapper(ResponseHeadersMapper.class);

        String json = "application/json";
        String location = "/v1/consents/123456";
        String xRequestId = "1234567";
        String scaApproach = "embedded";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(CONTENT_TYPE, Collections.singletonList(json));
        httpHeaders.put(LOCATION, Collections.singletonList(location));
        httpHeaders.put(X_REQUEST_ID, Collections.singletonList(xRequestId));
        httpHeaders.put(ASPSP_SCA_APPROACH, Collections.singletonList(scaApproach));

        ResponseHeaders headers = mapper.getHeaders(httpHeaders);

        assertThat(headers.getHeadersMap()).hasSize(4);
        assertThat(headers.getHeader(CONTENT_TYPE)).isEqualTo(json);
        assertThat(headers.getHeader(LOCATION)).isEqualTo(location);
        assertThat(headers.getHeader(X_REQUEST_ID)).isEqualTo(xRequestId);
        assertThat(headers.getHeader(ASPSP_SCA_APPROACH)).isEqualTo(scaApproach);
    }
}
