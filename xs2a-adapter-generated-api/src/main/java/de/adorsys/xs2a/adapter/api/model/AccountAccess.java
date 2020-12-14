package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class AccountAccess {
    private List<AccountReference> accounts;

    private List<AccountReference> balances;

    private List<AccountReference> transactions;

    private AdditionalInformationAccess additionalInformation;

    private AvailableAccounts availableAccounts;

    private AvailableAccountsWithBalance availableAccountsWithBalance;

    private AllPsd2 allPsd2;

    private List<String> restrictedTo;

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

    public AdditionalInformationAccess getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformationAccess additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AvailableAccounts getAvailableAccounts() {
        return availableAccounts;
    }

    public void setAvailableAccounts(AvailableAccounts availableAccounts) {
        this.availableAccounts = availableAccounts;
    }

    public AvailableAccountsWithBalance getAvailableAccountsWithBalance() {
        return availableAccountsWithBalance;
    }

    public void setAvailableAccountsWithBalance(
        AvailableAccountsWithBalance availableAccountsWithBalance) {
        this.availableAccountsWithBalance = availableAccountsWithBalance;
    }

    public AllPsd2 getAllPsd2() {
        return allPsd2;
    }

    public void setAllPsd2(AllPsd2 allPsd2) {
        this.allPsd2 = allPsd2;
    }

    public List<String> getRestrictedTo() {
        return restrictedTo;
    }

    public void setRestrictedTo(List<String> restrictedTo) {
        this.restrictedTo = restrictedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountAccess that = (AccountAccess) o;
        return Objects.equals(accounts, that.accounts) &&
            Objects.equals(balances, that.balances) &&
            Objects.equals(transactions, that.transactions) &&
            Objects.equals(additionalInformation, that.additionalInformation) &&
            Objects.equals(availableAccounts, that.availableAccounts) &&
            Objects.equals(availableAccountsWithBalance, that.availableAccountsWithBalance) &&
            Objects.equals(allPsd2, that.allPsd2) &&
            Objects.equals(restrictedTo, that.restrictedTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts,
            balances,
            transactions,
            additionalInformation,
            availableAccounts,
            availableAccountsWithBalance,
            allPsd2,
            restrictedTo);
    }

    public enum AvailableAccounts {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHOWNERNAME("allAccountsWithOwnerName");

        private String value;

        AvailableAccounts(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AvailableAccounts fromValue(String value) {
            for (AvailableAccounts e : AvailableAccounts.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    public enum AvailableAccountsWithBalance {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHOWNERNAME("allAccountsWithOwnerName");

        private String value;

        AvailableAccountsWithBalance(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AvailableAccountsWithBalance fromValue(String value) {
            for (AvailableAccountsWithBalance e : AvailableAccountsWithBalance.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    public enum AllPsd2 {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHOWNERNAME("allAccountsWithOwnerName");

        private String value;

        AllPsd2(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AllPsd2 fromValue(String value) {
            for (AllPsd2 e : AllPsd2.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
