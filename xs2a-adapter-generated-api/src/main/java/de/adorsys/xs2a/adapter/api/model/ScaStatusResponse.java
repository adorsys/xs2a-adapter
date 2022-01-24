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

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ScaStatusResponse {
    private ScaStatus scaStatus;

    private String psuMessage;

    private Boolean trustedBeneficiaryFlag;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private List<TppMessageGeneric> tppMessage;

    public ScaStatus getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(ScaStatus scaStatus) {
        this.scaStatus = scaStatus;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public Boolean getTrustedBeneficiaryFlag() {
        return trustedBeneficiaryFlag;
    }

    public void setTrustedBeneficiaryFlag(Boolean trustedBeneficiaryFlag) {
        this.trustedBeneficiaryFlag = trustedBeneficiaryFlag;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    public List<TppMessageGeneric> getTppMessage() {
        return tppMessage;
    }

    public void setTppMessage(List<TppMessageGeneric> tppMessage) {
        this.tppMessage = tppMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScaStatusResponse that = (ScaStatusResponse) o;
        return Objects.equals(scaStatus, that.scaStatus) &&
            Objects.equals(psuMessage, that.psuMessage) &&
            Objects.equals(trustedBeneficiaryFlag, that.trustedBeneficiaryFlag) &&
            Objects.equals(links, that.links) &&
            Objects.equals(tppMessage, that.tppMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scaStatus,
            psuMessage,
            trustedBeneficiaryFlag,
            links,
            tppMessage);
    }
}
