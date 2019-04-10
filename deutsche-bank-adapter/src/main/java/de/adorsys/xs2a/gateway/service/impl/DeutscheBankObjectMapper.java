/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.gateway.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

class DeutscheBankObjectMapper extends ObjectMapper {

    DeutscheBankObjectMapper() {
        registerModule(new JavaTimeModule());
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    <T> String toString(T body) {
        try {
            return super.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    <T> T toObject(InputStream inputStream, Class<T> klass) {
        try {
            return super.readValue(inputStream, klass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
