/*
 * Copyright 2018-2019 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

/**
 * Requested access services for a consent.
 */
public class AccountAccess {
    private List<AccountReference> accounts;
    private List<AccountReference> balances;
    private List<AccountReference> transactions;
    private AvailableAccountsEnum availableAccounts;
    private AllPsd2Enum allPsd2;

    public List<AccountReference> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountReference> accounts) {
        this.accounts = accounts;
    }

    public List<AccountReference> getBalances() {
        return balances;
    }

    public void setBalances(List<AccountReference> balances) {
        this.balances = balances;
    }

    public List<AccountReference> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<AccountReference> transactions) {
        this.transactions = transactions;
    }

    public AvailableAccountsEnum getAvailableAccounts() {
        return availableAccounts;
    }

    public void setAvailableAccounts(AvailableAccountsEnum availableAccounts) {
        this.availableAccounts = availableAccounts;
    }

    public AllPsd2Enum getAllPsd2() {
        return allPsd2;
    }

    public void setAllPsd2(AllPsd2Enum allPsd2) {
        this.allPsd2 = allPsd2;
    }

    public enum AvailableAccountsEnum {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHBALANCES("allAccountsWithBalances");

        private String value;

        AvailableAccountsEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static AvailableAccountsEnum fromValue(String text) {
            for (AvailableAccountsEnum b : AvailableAccountsEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    public enum AllPsd2Enum {
        ALLACCOUNTS("allAccounts");

        private String value;

        AllPsd2Enum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static AllPsd2Enum fromValue(String text) {
            for (AllPsd2Enum b : AllPsd2Enum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
