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

public class OauthState {
    private String clientId;
    private String aspspId;

    public OauthState() {
    }

    public OauthState(String clientId, String aspspId) {
        this.clientId = clientId;
        this.aspspId = aspspId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAspspId() {
        return aspspId;
    }

    public void setAspspId(String aspspId) {
        this.aspspId = aspspId;
    }

    public String toJson() {
        return "{\"clientId\":\"" + clientId + "\",\"aspspId\":\"" + aspspId + "\"}";
    }

    @Override
    public String toString() {
        return toJson();
    }
}
