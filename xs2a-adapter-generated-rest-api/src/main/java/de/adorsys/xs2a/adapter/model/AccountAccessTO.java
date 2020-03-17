package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class AccountAccessTO {
    private List<AccountReferenceTO> accounts;

    private List<AccountReferenceTO> balances;

    private List<AccountReferenceTO> transactions;

    private AdditionalInformationAccessTO additionalInformation;

    private AvailableAccountsTO availableAccounts;

    private AvailableAccountsWithBalanceTO availableAccountsWithBalance;

    private AllPsd2TO allPsd2;

    public List<AccountReferenceTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountReferenceTO> accounts) {
        this.accounts = accounts;
    }

    public List<AccountReferenceTO> getBalances() {
        return balances;
    }

    public void setBalances(List<AccountReferenceTO> balances) {
        this.balances = balances;
    }

    public List<AccountReferenceTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<AccountReferenceTO> transactions) {
        this.transactions = transactions;
    }

    public AdditionalInformationAccessTO getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(AdditionalInformationAccessTO additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public AvailableAccountsTO getAvailableAccounts() {
        return availableAccounts;
    }

    public void setAvailableAccounts(AvailableAccountsTO availableAccounts) {
        this.availableAccounts = availableAccounts;
    }

    public AvailableAccountsWithBalanceTO getAvailableAccountsWithBalance() {
        return availableAccountsWithBalance;
    }

    public void setAvailableAccountsWithBalance(
        AvailableAccountsWithBalanceTO availableAccountsWithBalance) {
        this.availableAccountsWithBalance = availableAccountsWithBalance;
    }

    public AllPsd2TO getAllPsd2() {
        return allPsd2;
    }

    public void setAllPsd2(AllPsd2TO allPsd2) {
        this.allPsd2 = allPsd2;
    }

    public enum AvailableAccountsTO {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHOWNERNAME("allAccountsWithOwnerName");

        private String value;

        AvailableAccountsTO(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AvailableAccountsTO fromValue(String value) {
            for (AvailableAccountsTO e : AvailableAccountsTO.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(value);
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    public enum AvailableAccountsWithBalanceTO {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHOWNERNAME("allAccountsWithOwnerName");

        private String value;

        AvailableAccountsWithBalanceTO(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AvailableAccountsWithBalanceTO fromValue(String value) {
            for (AvailableAccountsWithBalanceTO e : AvailableAccountsWithBalanceTO.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(value);
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    public enum AllPsd2TO {
        ALLACCOUNTS("allAccounts"),

        ALLACCOUNTSWITHOWNERNAME("allAccountsWithOwnerName");

        private String value;

        AllPsd2TO(String value) {
            this.value = value;
        }

        @JsonCreator
        public static AllPsd2TO fromValue(String value) {
            for (AllPsd2TO e : AllPsd2TO.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            throw new IllegalArgumentException(value);
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
