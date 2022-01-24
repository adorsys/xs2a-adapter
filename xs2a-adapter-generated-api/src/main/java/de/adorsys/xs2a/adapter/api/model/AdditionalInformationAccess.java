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

import javax.annotation.Generated;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AdditionalInformationAccess {
    private List<AccountReference> ownerName;

    private List<AccountReference> trustedBeneficiaries;

    public List<AccountReference> getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(List<AccountReference> ownerName) {
        this.ownerName = ownerName;
    }

    public List<AccountReference> getTrustedBeneficiaries() {
        return trustedBeneficiaries;
    }

    public void setTrustedBeneficiaries(List<AccountReference> trustedBeneficiaries) {
        this.trustedBeneficiaries = trustedBeneficiaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalInformationAccess that = (AdditionalInformationAccess) o;
        return Objects.equals(ownerName, that.ownerName) &&
            Objects.equals(trustedBeneficiaries, that.trustedBeneficiaries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerName,
            trustedBeneficiaries);
    }
}
