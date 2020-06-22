package de.adorsys.xs2a.adapter.api.model;

import javax.annotation.Generated;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ReadCardAccountBalanceResponse200 {
    private AccountReference cardAccount;

    private List<Balance> balances;

    public AccountReference getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(AccountReference cardAccount) {
        this.cardAccount = cardAccount;
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
            Objects.equals(balances, that.balances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardAccount,
            balances);
    }
}
