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
public class CardAccountsTransactionsResponse200 {
    private AccountReference cardAccount;

    private Boolean debitAccounting;

    private CardAccountReport cardTransactions;

    private List<Balance> balances;

    @JsonProperty("_links")
    private Map<String, HrefType> links;

    public AccountReference getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(AccountReference cardAccount) {
        this.cardAccount = cardAccount;
    }

    public Boolean getDebitAccounting() {
        return debitAccounting;
    }

    public void setDebitAccounting(Boolean debitAccounting) {
        this.debitAccounting = debitAccounting;
    }

    public CardAccountReport getCardTransactions() {
        return cardTransactions;
    }

    public void setCardTransactions(CardAccountReport cardTransactions) {
        this.cardTransactions = cardTransactions;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public Map<String, HrefType> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefType> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardAccountsTransactionsResponse200 that = (CardAccountsTransactionsResponse200) o;
        return Objects.equals(cardAccount, that.cardAccount) &&
            Objects.equals(debitAccounting, that.debitAccounting) &&
            Objects.equals(cardTransactions, that.cardTransactions) &&
            Objects.equals(balances, that.balances) &&
            Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardAccount,
            debitAccounting,
            cardTransactions,
            balances,
            links);
    }
}
