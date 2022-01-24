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

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Arrays;

public enum WiremockFileType {
    AIS_CREATE_CONSENT("ais-create-consent.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method) && CONSENTS_URI.equals(url);
        }

    },
    AIS_START_PSU_AUTHENTICATION("ais-start-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && url.endsWith(AUTHORISATIONS_URI);
        }
    },
    AIS_UPDATE_PSU_AUTHENTICATION("ais-update-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && body.contains(PSU_DATA);
        }
    },
    AIS_SELECT_SCA_METHOD("ais-select-sca-method.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && body.contains(AUTHENTICATION_METHOD_ID);
        }
    },
    AIS_SEND_OTP("ais-send-otp.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && body.contains(SCA_AUTHENTICATION_DATA);
        }
    },
    AIS_GET_ACCOUNTS("ais-get-accounts.json") {
        @Override
        public boolean check(String url, String method, String body) {
            try {
                URIBuilder uriBuilder = new URIBuilder(url);
                return GET_METHOD.equalsIgnoreCase(method)
                           && uriBuilder.getPath().endsWith(ACCOUNTS_URI);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return false;
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
                       && url.contains(AUTHORISATIONS_URI);
        }
    },
    AIS_GET_CONSENT_STATUS("ais-get-consent-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                       && url.startsWith(CONSENTS_URI)
                       && url.endsWith(STATUS_URI);
        }
    },
    // Payment Initiation
    PIS_PAYMENTS_SCT_INITIATE_PAYMENT("pis-payments-sct-initiate-payment.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.equals(PAYMENTS_SCT_URI);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_INITIATE_PAYMENT("pis-payments-pain001-sct-initiate-payment.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.equals(PAYMENTS_PAIN001_SCT_URI);
        }
    },
    PIS_PERIODIC_SCT_INITIATE_PAYMENT("pis-periodic-sct-initiate-payment.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.equals(PERIODIC_SCT_URI);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_INITIATE_PAYMENT("pis-periodic-pain001-sct-initiate-payment.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.equals(PERIODIC_PAIN001_CST_URI);
        }
    },
    // Start PSU Authentication
    PIS_PAYMENTS_SCT_START_PSU_AUTHENTICATION("pis-payments-sct-start-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_SCT_URI)
                && url.endsWith(AUTHORISATIONS_URI);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_START_PSU_AUTHENTICATION("pis-payments-pain001-sct-start-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_PAIN001_SCT_URI)
                && url.endsWith(AUTHORISATIONS_URI);
        }
    },
    PIS_PERIODIC_SCT_START_PSU_AUTHENTICATION("pis-periodic-sct-start-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_SCT_URI)
                && url.endsWith(AUTHORISATIONS_URI);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_START_PSU_AUTHENTICATION("pis-periodic-pain001-sct-start-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return POST_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_PAIN001_CST_URI)
                && url.endsWith(AUTHORISATIONS_URI);
        }
    },
    // Update PSU Authentication
    PIS_PAYMENTS_SCT_UPDATE_PSU_AUTHENTICATION("pis-payments-sct-update-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_SCT_URI)
                && body.contains(PSU_DATA);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION("pis-payments-pain001-sct-update-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_PAIN001_SCT_URI)
                && body.contains(PSU_DATA);
        }
    },
    PIS_PERIODIC_SCT_UPDATE_PSU_AUTHENTICATION("pis-periodic-sct-update-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_SCT_URI)
                && body.contains(PSU_DATA);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION("pis-periodic-pain001-sct-update-psu-authentication.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_PAIN001_CST_URI)
                && body.contains(PSU_DATA);
        }
    },
    // Select SCA Method
    PIS_PAYMENTS_SCT_SELECT_SCA_METHOD("pis-payments-sct-select-sca-method.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_SCT_URI)
                && body.contains(AUTHENTICATION_METHOD_ID);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_SELECT_SCA_METHOD("pis-payments-pain001-sct-select-sca-method.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_PAIN001_SCT_URI)
                && body.contains(AUTHENTICATION_METHOD_ID);
        }
    },
    PIS_PERIODIC_SCT_SELECT_SCA_METHOD("pis-periodic-sct-select-sca-method.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_SCT_URI)
                && body.contains(AUTHENTICATION_METHOD_ID);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_SELECT_SCA_METHOD("pis-periodic-pain001-sct-select-sca-method.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_PAIN001_CST_URI)
                && body.contains(AUTHENTICATION_METHOD_ID);
        }
    },
    // Send OTP
    PIS_PAYMENTS_SCT_SEND_OTP("pis-payments-sct-send-otp.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_SCT_URI)
                && body.contains(SCA_AUTHENTICATION_DATA);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_SEND_OTP("pis-payments-pain001-sct-send-otp.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_PAIN001_SCT_URI)
                && body.contains(SCA_AUTHENTICATION_DATA);
        }
    },
    PIS_PERIODIC_SCT_SEND_OTP("pis-periodic-sct-send-otp.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_SCT_URI)
                && body.contains(SCA_AUTHENTICATION_DATA);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_SEND_OTP("pis-periodic-pain001-sct-send-otp.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return PUT_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_PAIN001_CST_URI)
                && body.contains(SCA_AUTHENTICATION_DATA);
        }
    },
    // Get Transaction Status
    PIS_PAYMENTS_SCT_GET_TRANSACTION_STATUS("pis-payments-sct-get-payment-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_SCT_URI)
                && url.endsWith(STATUS_URI);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_GET_TRANSACTION_STATUS("pis-payments-pain001-sct-get-payment-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_PAIN001_SCT_URI)
                && url.endsWith(STATUS_URI);
        }
    },
    PIS_PERIODIC_SCT_GET_TRANSACTION_STATUS("pis-periodic-sct-get-payment-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_SCT_URI)
                && url.endsWith(STATUS_URI);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_GET_TRANSACTION_STATUS("pis-periodic-pain001-sct-get-payment-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_PAIN001_CST_URI)
                && url.endsWith(STATUS_URI);
        }
    },
    // Get SCA Status
    PIS_PAYMENTS_SCT_GET_SCA_STATUS("pis-payments-sct-get-sca-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_SCT_URI)
                && url.contains(AUTHORISATIONS_URI);
        }
    },
    PIS_PAYMENTS_PAIN001_SCT_GET_SCA_STATUS("pis-payments-pain001-sct-get-sca-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PAYMENTS_PAIN001_SCT_URI)
                && url.contains(AUTHORISATIONS_URI);
        }
    },
    PIS_PERIODIC_SCT_GET_SCA_STATUS("pis-periodic-sct-get-sca-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_SCT_URI)
                && url.contains(AUTHORISATIONS_URI);
        }
    },
    PIS_PERIODIC_PAIN001_SCT_GET_SCA_STATUS("pis-periodic-pain001-sct-get-sca-status.json") {
        @Override
        public boolean check(String url, String method, String body) {
            return GET_METHOD.equalsIgnoreCase(method)
                && url.startsWith(PERIODIC_PAIN001_CST_URI)
                && url.contains(AUTHORISATIONS_URI);
        }
    };

    private static final String SCA_AUTHENTICATION_DATA = "scaAuthenticationData";
    private static final String AUTHENTICATION_METHOD_ID = "authenticationMethodId";
    private static final String PSU_DATA = "psuData";
    private static final String DELETE_METHOD = "DELETE";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String GET_METHOD = "GET";
    private static final String CONSENTS_URI = "/v1/consents";
    private static final String ACCOUNTS_URI = "/v1/accounts";
    private static final String AUTHORISATIONS_URI = "/authorisations";
    private static final String STATUS_URI = "/status";
    private static final String PAYMENTS_SCT_URI = "/v1/payments/sepa-credit-transfers";
    private static final String PAYMENTS_PAIN001_SCT_URI = "/v1/payments/pain.001-sepa-credit-transfers";
    private static final String PERIODIC_SCT_URI = "/v1/periodic-payments/sepa-credit-transfers";
    private static final String PERIODIC_PAIN001_CST_URI = "/v1/periodic-payments/pain.001-sepa-credit-transfers";
    private final String filename;

    WiremockFileType(String filename) {
        this.filename = filename;
    }

    public abstract boolean check(String url, String method, String body);

    public String getFileName() {
        return filename;
    }

    public static WiremockFileType resolve(String url, String method, String body) {
        return Arrays.stream(values())
                   .filter(r -> r.check(url, method, body))
                   .findFirst()
                   .orElseThrow(() -> new IllegalStateException("Can't resolve wiremock stub"));
    }
}
