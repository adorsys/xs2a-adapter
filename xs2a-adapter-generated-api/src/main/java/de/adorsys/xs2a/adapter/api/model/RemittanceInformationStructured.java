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
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class RemittanceInformationStructured {
    private String reference;

    private String referenceType;

    private String referenceIssuer;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceIssuer() {
        return referenceIssuer;
    }

    public void setReferenceIssuer(String referenceIssuer) {
        this.referenceIssuer = referenceIssuer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemittanceInformationStructured that = (RemittanceInformationStructured) o;
        return Objects.equals(reference, that.reference) &&
            Objects.equals(referenceType, that.referenceType) &&
            Objects.equals(referenceIssuer, that.referenceIssuer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference,
            referenceType,
            referenceIssuer);
    }
}
