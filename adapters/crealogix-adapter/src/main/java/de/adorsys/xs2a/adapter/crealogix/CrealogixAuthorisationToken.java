/*
 * Copyright 2018-2020 adorsys GmbH & Co KG
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

package de.adorsys.xs2a.adapter.crealogix;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class CrealogixAuthorisationToken {
    private String tppToken;
    private String psd2AuthorisationToken;

    CrealogixAuthorisationToken() {
    }

    public CrealogixAuthorisationToken(String tppToken, String psd2AuthorisationToken) {
        this.tppToken = tppToken;
        this.psd2AuthorisationToken = psd2AuthorisationToken;
    }

    public String getTppToken() {
        return tppToken;
    }

    public String getPsd2AuthorisationToken() {
        return psd2AuthorisationToken;
    }

    public String encode() {
        return encode(this);
    }

    public static String encode(CrealogixAuthorisationToken token) {
        return Base64.getEncoder().encodeToString(token.toString().getBytes());
    }

    public static CrealogixAuthorisationToken decode(String encodedToken) throws IOException {
        return new ObjectMapper().readValue(Base64.getDecoder().decode(encodedToken), CrealogixAuthorisationToken.class);
    }

    @Override
    public String toString() {
        return '{' +
                   "\"tppToken\":\"" + tppToken + '"' +
                   ", \"psd2AuthorisationToken\":\"" + psd2AuthorisationToken + '"' +
                   '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrealogixAuthorisationToken that = (CrealogixAuthorisationToken) o;
        return Objects.equals(tppToken, that.tppToken) &&
                   Objects.equals(psd2AuthorisationToken, that.psd2AuthorisationToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tppToken, psd2AuthorisationToken);
    }
}
