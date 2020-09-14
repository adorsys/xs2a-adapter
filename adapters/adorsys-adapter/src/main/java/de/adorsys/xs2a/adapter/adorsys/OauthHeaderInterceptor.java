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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.Request;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OauthHeaderInterceptor implements Request.Builder.Interceptor {

    private static final String BANK_CODE_FOR_OAUTH = "adorsys.oauth_approach.bank_code";
    private static final String OAUTH_HEADER_VALUE = "adorsys.oauth_approach.header_value";
    static final String OAUTH_HEADER_NAME = "adorsys.oauth_approach.header_name";

    @Override
    public void accept(Request.Builder builder) {
        String oauthBankCode = AdapterConfig.readProperty(BANK_CODE_FOR_OAUTH, "");
        List<String> bankCodes = Arrays.stream(oauthBankCode.split(","))
                                     .map(String::trim).collect(Collectors.toList());
        String requestBankCode = builder.headers().get(RequestHeaders.X_GTW_BANK_CODE);

        if (oauthBankCode.isEmpty() || !bankCodes.contains(requestBankCode)) {
            return;
        }

        String oauthHeaderName = AdapterConfig.readProperty(OAUTH_HEADER_NAME, "");

        if (builder.headers().containsKey(oauthHeaderName)) {
            return builder;
        }

        String headerValue = getOauthHeaderValue(bankCodes, requestBankCode);

        if (!oauthHeaderName.isEmpty() && !headerValue.isEmpty()) {
            builder.header(oauthHeaderName, headerValue);
        }
    }

    private String getOauthHeaderValue(List<String> oauthBankCodes, String requestBankCode) {
        int idx = oauthBankCodes.indexOf(requestBankCode);
        String headerValue = AdapterConfig.readProperty(OAUTH_HEADER_VALUE, "true");
        List<String> headerValues = Arrays.stream(headerValue.split(","))
                                        .map(String::trim).collect(Collectors.toList());
        return headerValues.size() > idx ? headerValues.get(idx) : "";
    }
}
