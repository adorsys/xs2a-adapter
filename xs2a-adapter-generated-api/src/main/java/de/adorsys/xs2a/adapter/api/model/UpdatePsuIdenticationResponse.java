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
public class UpdatePsuIdenticationResponse {
    private Amount transactionFees;

    private Amount currencyConversionFees;

    private Amount estimatedTotalAmount;

    private Amount estimatedInterbankSettlementAmount;

    private List<AuthenticationObject> scaMethods;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private ScaStatus scaStatus;

    private String psuMessage;

    public Amount getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Amount transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Amount getCurrencyConversionFees() {
        return currencyConversionFees;
    }

    public void setCurrencyConversionFees(Amount currencyConversionFees) {
        this.currencyConversionFees = currencyConversionFees;
    }

    public Amount getEstimatedTotalAmount() {
        return estimatedTotalAmount;
    }

    public void setEstimatedTotalAmount(Amount estimatedTotalAmount) {
        this.estimatedTotalAmount = estimatedTotalAmount;
    }

    public Amount getEstimatedInterbankSettlementAmount() {
        return estimatedInterbankSettlementAmount;
    }

    public void setEstimatedInterbankSettlementAmount(Amount estimatedInterbankSettlementAmount) {
        this.estimatedInterbankSettlementAmount = estimatedInterbankSettlementAmount;
    }

    public List<AuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<AuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePsuIdenticationResponse that = (UpdatePsuIdenticationResponse) o;
        return Objects.equals(transactionFees, that.transactionFees) &&
            Objects.equals(currencyConversionFees, that.currencyConversionFees) &&
            Objects.equals(estimatedTotalAmount, that.estimatedTotalAmount) &&
            Objects.equals(estimatedInterbankSettlementAmount, that.estimatedInterbankSettlementAmount) &&
            Objects.equals(scaMethods, that.scaMethods) &&
            Objects.equals(links, that.links) &&
            Objects.equals(scaStatus, that.scaStatus) &&
            Objects.equals(psuMessage, that.psuMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionFees,
            currencyConversionFees,
            estimatedTotalAmount,
            estimatedInterbankSettlementAmount,
            scaMethods,
            links,
            scaStatus,
            psuMessage);
    }
}
