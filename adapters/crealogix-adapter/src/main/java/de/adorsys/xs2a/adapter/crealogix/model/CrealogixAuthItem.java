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

package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.Objects;

public class CrealogixAuthItem {
    private String authOptionId;

    private CrealogixAuthType authType;

    private String authInfo;

    public String getAuthOptionId() {
        return authOptionId;
    }

    public void setAuthOptionId(String authOptionId) {
        this.authOptionId = authOptionId;
    }

    public CrealogixAuthType getAuthType() {
        return authType;
    }

    public void setAuthType(CrealogixAuthType authType) {
        this.authType = authType;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrealogixAuthItem that = (CrealogixAuthItem) o;
        return Objects.equals(authOptionId, that.authOptionId) &&
            authType == that.authType &&
            Objects.equals(authInfo, that.authInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authOptionId, authType, authInfo);
    }
}
