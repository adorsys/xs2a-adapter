package de.adorsys.xs2a.adapter.v139.api.model;

import java.lang.Boolean;
import java.lang.Object;
import java.lang.Override;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public class ReadCardAccountBalanceResponse200 {
  private AccountReference cardAccount;

  private Boolean debitAccounting;

  private List<Balance> balances;

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

  public List<Balance> getBalances() {
    return balances;
  }

  public void setBalances(List<Balance> balances) {
    this.balances = balances;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ReadCardAccountBalanceResponse200 that = (ReadCardAccountBalanceResponse200) o;
    return Objects.equals(cardAccount, that.cardAccount) &&
        Objects.equals(debitAccounting, that.debitAccounting) &&
        Objects.equals(balances, that.balances);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardAccount,
        debitAccounting,
        balances);
  }
}
