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

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import java.util.Arrays;

public enum WiremockFileResolver {
    AIS_CREATE_CONSENT("ais-create-consent.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method) && CONSENTS_URI.equals(url);
        }

    },
    AIS_AUTHORISE_PSU("ais-authorise-psu.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && body.contains("psuData");
        }
    },
    AIS_SELECT_SCA_METHOD("ais-select-sca-method.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && body.contains("authenticationMethodId");
        }
    },
    AIS_SEND_OTP("ais-send-otp.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && body.contains("scaAuthenticationData");
        }
    },
    AIS_GET_ACCOUNTS("ais-get-accounts.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(ACCOUNTS_URI);
        }
    },
    AIS_GET_BALANCES("ais-get-balances.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(ACCOUNTS_URI)
                       && url.endsWith("/balances");
        }
    },
    AIS_GET_TRANSACTIONS("ais-get-transactions.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(ACCOUNTS_URI)
                       && url.contains("/transactions");
        }
    },
    AIS_DELETE_CONSENT("ais-delete-consent.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return DELETE_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI);
        }
    },
    AIS_GET_SCA_STATUS("ais-get-sca-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && url.contains("/authorisations/");
        }
    },
    AIS_GET_CONSENT_STATUS("ais-get-consent-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && url.endsWith("/status");
        }
    };

    private static final String DELETE_METHOD = "DELETE";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String GET_METHOD = "GET";
    private static final String CONSENTS_URI = "/v1/consents";
    private static final String ACCOUNTS_URI = "/v1/accounts";
    private final String filename;

    WiremockFileResolver(String filename) {
        this.filename = filename;
    }

    public abstract boolean check(String url, String method, String body);

    public String getFileName() {
        return filename;
    }

    public static WiremockFileResolver resolve(String url, String method, String body) {
        return Arrays.stream(values())
                   .filter(r -> r.check(url, method, body))
                   .findFirst()
                   .orElseThrow(() -> new IllegalStateException("Can't resolve wiremock stub"));
    }
}
