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

package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum MessageCode {
    FORMAT_ERROR("FORMAT_ERROR"),

    PARAMETER_NOT_CONSISTENT("PARAMETER_NOT_CONSISTENT"),

    PARAMETER_NOT_SUPPORTED("PARAMETER_NOT_SUPPORTED"),

    SERVICE_INVALID("SERVICE_INVALID"),

    RESOURCE_UNKNOWN("RESOURCE_UNKNOWN"),

    RESOURCE_EXPIRED("RESOURCE_EXPIRED"),

    RESOURCE_BLOCKED("RESOURCE_BLOCKED"),

    TIMESTAMP_INVALID("TIMESTAMP_INVALID"),

    PERIOD_INVALID("PERIOD_INVALID"),

    SCA_METHOD_UNKNOWN("SCA_METHOD_UNKNOWN"),

    SCA_INVALID("SCA_INVALID"),

    CONSENT_UNKNOWN("CONSENT_UNKNOWN"),

    SESSIONS_NOT_SUPPORTED("SESSIONS_NOT_SUPPORTED"),

    PAYMENT_FAILED("PAYMENT_FAILED"),

    EXECUTION_DATE_INVALID("EXECUTION_DATE_INVALID"),

    CERTIFICATE_INVALID("CERTIFICATE_INVALID"),

    ROLE_INVALID("ROLE_INVALID"),

    CERTIFICATE_EXPIRED("CERTIFICATE_EXPIRED"),

    CERTIFICATE_BLOCKED("CERTIFICATE_BLOCKED"),

    CERTIFICATE_REVOKE("CERTIFICATE_REVOKE"),

    CERTIFICATE_MISSING("CERTIFICATE_MISSING"),

    SIGNATURE_INVALID("SIGNATURE_INVALID"),

    SIGNATURE_MISSING("SIGNATURE_MISSING"),

    CORPORATE_ID_INVALID("CORPORATE_ID_INVALID"),

    PSU_CREDENTIALS_INVALID("PSU_CREDENTIALS_INVALID"),

    CONSENT_INVALID("CONSENT_INVALID"),

    CONSENT_EXPIRED("CONSENT_EXPIRED"),

    TOKEN_UNKNOWN("TOKEN_UNKNOWN"),

    TOKEN_INVALID("TOKEN_INVALID"),

    TOKEN_EXPIRED("TOKEN_EXPIRED"),

    REQUIRED_KID_MISSING("REQUIRED_KID_MISSING"),

    SERVICE_BLOCKED("SERVICE_BLOCKED"),

    PRODUCT_INVALID("PRODUCT_INVALID"),

    PRODUCT_UNKNOWN("PRODUCT_UNKNOWN"),

    REQUESTED_FORMATS_INVALID("REQUESTED_FORMATS_INVALID"),

    STATUS_INVALID("STATUS_INVALID"),

    ACCESS_EXCEEDED("ACCESS_EXCEEDED");

    private String value;

    MessageCode(String value) {
        this.value = value;
    }

    @JsonCreator
    public static MessageCode fromValue(String value) {
        for (MessageCode e : MessageCode.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
