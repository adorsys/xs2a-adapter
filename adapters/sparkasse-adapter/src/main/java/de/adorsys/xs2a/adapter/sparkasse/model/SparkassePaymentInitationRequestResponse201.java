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

package de.adorsys.xs2a.adapter.sparkasse.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.adorsys.xs2a.adapter.api.model.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SparkassePaymentInitationRequestResponse201 {
    private TransactionStatus transactionStatus;

    private String paymentId;

    private Amount transactionFees;

    private Amount currencyConversionFee;

    private Amount estimatedTotalAmount;

    private Amount estimatedInterbankSettlementAmount;

    private Boolean transactionFeeIndicator;

    private List<SparkasseAuthenticationObject> scaMethods;

    private SparkasseAuthenticationObject chosenScaMethod;

    private ChallengeData challengeData;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    private String psuMessage;

    private List<TppMessage2XX> tppMessages;

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Amount getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Amount transactionFees) {
        this.transactionFees = transactionFees;
    }

    public Amount getCurrencyConversionFee() {
        return currencyConversionFee;
    }

    public void setCurrencyConversionFee(Amount currencyConversionFee) {
        this.currencyConversionFee = currencyConversionFee;
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

    public Boolean getTransactionFeeIndicator() {
        return transactionFeeIndicator;
    }

    public void setTransactionFeeIndicator(Boolean transactionFeeIndicator) {
        this.transactionFeeIndicator = transactionFeeIndicator;
    }

    public List<SparkasseAuthenticationObject> getScaMethods() {
        return scaMethods;
    }

    public void setScaMethods(List<SparkasseAuthenticationObject> scaMethods) {
        this.scaMethods = scaMethods;
    }

    public SparkasseAuthenticationObject getChosenScaMethod() {
        return chosenScaMethod;
    }

    public void setChosenScaMethod(SparkasseAuthenticationObject chosenScaMethod) {
        this.chosenScaMethod = chosenScaMethod;
    }

    public ChallengeData getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(ChallengeData challengeData) {
        this.challengeData = challengeData;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public List<TppMessage2XX> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TppMessage2XX> tppMessages) {
        this.tppMessages = tppMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SparkassePaymentInitationRequestResponse201 that = (SparkassePaymentInitationRequestResponse201) o;
        return Objects.equals(transactionStatus, that.transactionStatus) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(transactionFees, that.transactionFees) &&
            Objects.equals(currencyConversionFee, that.currencyConversionFee) &&
            Objects.equals(estimatedTotalAmount, that.estimatedTotalAmount) &&
            Objects.equals(estimatedInterbankSettlementAmount, that.estimatedInterbankSettlementAmount) &&
            Objects.equals(transactionFeeIndicator, that.transactionFeeIndicator) &&
            Objects.equals(scaMethods, that.scaMethods) &&
            Objects.equals(chosenScaMethod, that.chosenScaMethod) &&
            Objects.equals(challengeData, that.challengeData) &&
            Objects.equals(links, that.links) &&
            Objects.equals(psuMessage, that.psuMessage) &&
            Objects.equals(tppMessages, that.tppMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionStatus,
            paymentId,
            transactionFees,
            currencyConversionFee,
            estimatedTotalAmount,
            estimatedInterbankSettlementAmount,
            transactionFeeIndicator,
            scaMethods,
            chosenScaMethod,
            challengeData,
            links,
            psuMessage,
            tppMessages);
    }
}
