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

package de.adorsys.xs2a.adapter.fiducia.model;

import java.util.Objects;

public class FiduciaAdditionalInformationStructured {
    private FiduciaStandingOrderDetails standingOrderDetails;

    public FiduciaStandingOrderDetails getStandingOrderDetails() {
        return standingOrderDetails;
    }

    public void setStandingOrderDetails(FiduciaStandingOrderDetails standingOrderDetails) {
        this.standingOrderDetails = standingOrderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiduciaAdditionalInformationStructured that = (FiduciaAdditionalInformationStructured) o;
        return Objects.equals(standingOrderDetails, that.standingOrderDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standingOrderDetails);
    }
}
