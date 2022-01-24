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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class CardTransaction {
    private String cardTransactionId;

    private String terminalId;

    private LocalDate transactionDate;

    private OffsetDateTime acceptorTransactionDateTime;

    private LocalDate bookingDate;

    private Amount transactionAmount;

    private List<ReportExchangeRate> currencyExchange;

    private Amount originalAmount;

    private Amount markupFee;

    private String markupFeePercentage;

    private String cardAcceptorId;

    private Address cardAcceptorAddress;

    private String cardAcceptorPhone;

    private String merchantCategoryCode;

    private String maskedPAN;

    private String transactionDetails;

    private Boolean invoiced;

    private String proprietaryBankTransactionCode;

    public String getCardTransactionId() {
        return cardTransactionId;
    }

    public void setCardTransactionId(String cardTransactionId) {
        this.cardTransactionId = cardTransactionId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public OffsetDateTime getAcceptorTransactionDateTime() {
        return acceptorTransactionDateTime;
    }

    public void setAcceptorTransactionDateTime(OffsetDateTime acceptorTransactionDateTime) {
        this.acceptorTransactionDateTime = acceptorTransactionDateTime;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public List<ReportExchangeRate> getCurrencyExchange() {
        return currencyExchange;
    }

    public void setCurrencyExchange(List<ReportExchangeRate> currencyExchange) {
        this.currencyExchange = currencyExchange;
    }

    public Amount getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Amount originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Amount getMarkupFee() {
        return markupFee;
    }

    public void setMarkupFee(Amount markupFee) {
        this.markupFee = markupFee;
    }

    public String getMarkupFeePercentage() {
        return markupFeePercentage;
    }

    public void setMarkupFeePercentage(String markupFeePercentage) {
        this.markupFeePercentage = markupFeePercentage;
    }

    public String getCardAcceptorId() {
        return cardAcceptorId;
    }

    public void setCardAcceptorId(String cardAcceptorId) {
        this.cardAcceptorId = cardAcceptorId;
    }

    public Address getCardAcceptorAddress() {
        return cardAcceptorAddress;
    }

    public void setCardAcceptorAddress(Address cardAcceptorAddress) {
        this.cardAcceptorAddress = cardAcceptorAddress;
    }

    public String getCardAcceptorPhone() {
        return cardAcceptorPhone;
    }

    public void setCardAcceptorPhone(String cardAcceptorPhone) {
        this.cardAcceptorPhone = cardAcceptorPhone;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getMaskedPAN() {
        return maskedPAN;
    }

    public void setMaskedPAN(String maskedPAN) {
        this.maskedPAN = maskedPAN;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public Boolean getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Boolean invoiced) {
        this.invoiced = invoiced;
    }

    public String getProprietaryBankTransactionCode() {
        return proprietaryBankTransactionCode;
    }

    public void setProprietaryBankTransactionCode(String proprietaryBankTransactionCode) {
        this.proprietaryBankTransactionCode = proprietaryBankTransactionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardTransaction that = (CardTransaction) o;
        return Objects.equals(cardTransactionId, that.cardTransactionId) &&
            Objects.equals(terminalId, that.terminalId) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(acceptorTransactionDateTime, that.acceptorTransactionDateTime) &&
            Objects.equals(bookingDate, that.bookingDate) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(currencyExchange, that.currencyExchange) &&
            Objects.equals(originalAmount, that.originalAmount) &&
            Objects.equals(markupFee, that.markupFee) &&
            Objects.equals(markupFeePercentage, that.markupFeePercentage) &&
            Objects.equals(cardAcceptorId, that.cardAcceptorId) &&
            Objects.equals(cardAcceptorAddress, that.cardAcceptorAddress) &&
            Objects.equals(cardAcceptorPhone, that.cardAcceptorPhone) &&
            Objects.equals(merchantCategoryCode, that.merchantCategoryCode) &&
            Objects.equals(maskedPAN, that.maskedPAN) &&
            Objects.equals(transactionDetails, that.transactionDetails) &&
            Objects.equals(invoiced, that.invoiced) &&
            Objects.equals(proprietaryBankTransactionCode, that.proprietaryBankTransactionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardTransactionId,
            terminalId,
            transactionDate,
            acceptorTransactionDateTime,
            bookingDate,
            transactionAmount,
            currencyExchange,
            originalAmount,
            markupFee,
            markupFeePercentage,
            cardAcceptorId,
            cardAcceptorAddress,
            cardAcceptorPhone,
            merchantCategoryCode,
            maskedPAN,
            transactionDetails,
            invoiced,
            proprietaryBankTransactionCode);
    }
}
