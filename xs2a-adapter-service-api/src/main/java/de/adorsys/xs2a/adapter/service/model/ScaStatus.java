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

package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * This data element is containing information about the status of the SCA method applied.
 */
public enum ScaStatus {
    /**
     * An authorisation or cancellation-authorisation resource has been created
     * successfully.
     */
    RECEIVED("received", false),
    /**
     * The PSU related to the authorisation or cancellation-authorisation resource has
     * been identified.
     */
    PSUIDENTIFIED("psuIdentified", false),
    /**
     * The PSU related to the authorisation or cancellation-authorisation resource
     * has been identified and authenticated e.g. by a password or by an access token.
     */
    PSUAUTHENTICATED("psuAuthenticated", false),
    /**
     * The PSU/TPP has selected the related SCA routine.
     * If the SCA method is chosen implicitly since only one SCA method is available,
     * then this is the first status to be reported instead of {@link #RECEIVED}
     */
    SCAMETHODSELECTED("scaMethodSelected", false),
    /**
     * The addressed SCA routine has been started.
     */
    STARTED("started", false),
    /**
     * SCA is technically successfully finalised by the PSU, but the
     * authorisation resource needs a confirmation command by the TPP
     * yet.
     */
    UNCONFIRMED("unconfirmed", true),
    /**
     * The SCA routine has been finalised successfully.
     */
    FINALISED("finalised", true),
    /**
     * The SCA routine failed.
     */
    FAILED("failed", true),
    /**
     * SCA was exempted for the related transaction,
     * the related authorisation is successful.
     */
    EXEMPTED("exempted", false);

    private static final Map<String, ScaStatus> HOLDER = new HashMap<>();

    static {
        for (ScaStatus status : ScaStatus.values()) {
            HOLDER.put(status.value.toLowerCase(), status);
        }
    }

    private String value;
    private final boolean finalisedStatus;

    ScaStatus(String value, boolean finalisedStatus) {
        this.value = value;
        this.finalisedStatus = finalisedStatus;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean isFinalisedStatus() {
        return finalisedStatus;
    }

    public boolean isNotFinalisedStatus() {
        return !isFinalisedStatus();
    }

    @JsonCreator
    public static ScaStatus fromValue(String text) {
        for (ScaStatus scaStatus : ScaStatus.values()) {
            if (String.valueOf(scaStatus.value).equals(text)) {
                return scaStatus;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
