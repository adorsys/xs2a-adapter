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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OauthHeaderInterceptor implements Interceptor {

    private static final String BANK_CODE_FOR_OAUTH = "adorsys.oauth_approach.bank_code";
    private static final String OAUTH_HEADER_VALUE = "adorsys.oauth_approach.header_value";
    static final String OAUTH_HEADER_NAME = "adorsys.oauth_approach.header_name";

    @Override
    public Request.Builder preHandle(Request.Builder builder) {
        String oauthBankCode = AdapterConfig.readProperty(BANK_CODE_FOR_OAUTH, "");
        List<String> bankCodes = Arrays.stream(oauthBankCode.split(","))
                                     .map(String::trim).collect(Collectors.toList());
        String requestBankCode = builder.headers().get(RequestHeaders.X_GTW_BANK_CODE);

        if (oauthBankCode.isEmpty() || !bankCodes.contains(requestBankCode)) {
            return builder;
        }

        String oauthHeaderName = AdapterConfig.readProperty(OAUTH_HEADER_NAME, "");

        if (builder.headers().containsKey(oauthHeaderName)) {
            return builder;
        }

        String headerValue = getOauthHeaderValue(bankCodes, requestBankCode);

        if (!oauthHeaderName.isEmpty() && !headerValue.isEmpty()) {
            builder.header(oauthHeaderName, headerValue);
        }

        return builder;
    }

    private String getOauthHeaderValue(List<String> oauthBankCodes, String requestBankCode) {
        int idx = oauthBankCodes.indexOf(requestBankCode);
        String headerValue = AdapterConfig.readProperty(OAUTH_HEADER_VALUE, "true");
        List<String> headerValues = Arrays.stream(headerValue.split(","))
                                        .map(String::trim).collect(Collectors.toList());
        return headerValues.size() > idx ? headerValues.get(idx) : "";
    }
}
