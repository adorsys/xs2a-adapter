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
public class PeriodicPaymentInitiationMultipartBody {
    private Object xml_sct;

    private PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json_standingorderType;

    public Object getXml_sct() {
        return xml_sct;
    }

    public void setXml_sct(Object xml_sct) {
        this.xml_sct = xml_sct;
    }

    public PeriodicPaymentInitiationXmlPart2StandingorderTypeJson getJson_standingorderType() {
        return json_standingorderType;
    }

    public void setJson_standingorderType(
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson json_standingorderType) {
        this.json_standingorderType = json_standingorderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodicPaymentInitiationMultipartBody that = (PeriodicPaymentInitiationMultipartBody) o;
        return Objects.equals(xml_sct, that.xml_sct) &&
            Objects.equals(json_standingorderType, that.json_standingorderType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xml_sct,
            json_standingorderType);
    }
}
