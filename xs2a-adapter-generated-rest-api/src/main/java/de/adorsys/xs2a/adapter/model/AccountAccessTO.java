package de.adorsys.xs2a.adapter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-codegen")
public class AccountAccessTO {
  private List<AccountReferenceTO> accounts;

  private List<AccountReferenceTO> balances;

  private List<AccountReferenceTO> transactions;

  private AvailableAccountsTO availableAccounts;

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

  public AvailableAccountsTO getAvailableAccounts() {
    return availableAccounts;
  }

  public void setAvailableAccounts(AvailableAccountsTO availableAccounts) {
    this.availableAccounts = availableAccounts;
  }

  public AllPsd2TO getAllPsd2() {
    return allPsd2;
  }

  public void setAllPsd2(AllPsd2TO allPsd2) {
    this.allPsd2 = allPsd2;
  }

  public enum AvailableAccountsTO {
    ALLACCOUNTS("allAccounts"),

    ALLACCOUNTSWITHBALANCES("allAccountsWithBalances");

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

  public enum AllPsd2TO {
    ALLACCOUNTS("allAccounts");

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
