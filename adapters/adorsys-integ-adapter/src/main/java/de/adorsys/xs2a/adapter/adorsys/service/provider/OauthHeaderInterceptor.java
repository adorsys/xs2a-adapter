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

package de.adorsys.xs2a.adapter.adorsys.service.provider;

import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;

public class OauthHeaderInterceptor implements Request.Builder.Interceptor {

    static final String OAUTH_HEADER_NAME = "adorsys.oauth_approach.header_name";
    static final String OAUTH_HEADER_VALUE = "adorsys.oauth_approach.header_value";
    static final String BANK_CODE_FOR_OAUTH = "adorsys.oauth_approach.bank_code";

    @Override
    public Request.Builder apply(Request.Builder builder) {
        String oauthBankCode = AdapterConfig.readProperty(BANK_CODE_FOR_OAUTH, "");
        String requestBankCode = builder.headers().get(RequestHeaders.X_GTW_BANK_CODE);

        if (oauthBankCode.isEmpty() || !oauthBankCode.equals(requestBankCode)) {
            return builder;
        }

        String oauthHeader = AdapterConfig.readProperty(OAUTH_HEADER_NAME, "");

        if (!oauthHeader.isEmpty()) {
            builder.header(oauthHeader, AdapterConfig.readProperty(OAUTH_HEADER_VALUE, "true"));
        }

        return builder;
    }
}
