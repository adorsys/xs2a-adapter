package de.adorsys.xs2a.adapter.v139.api.model;

import javax.annotation.Generated;
import java.util.List;
import java.util.Objects;

@Generated("xs2a-adapter-codegen")
public class ReadAccountBalanceResponse200 {
    private AccountReference account;

    private List<Balance> balances;

    public AccountReference getAccount() {
        return account;
    }

    public void setAccount(AccountReference account) {
        this.account = account;
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
        ReadAccountBalanceResponse200 that = (ReadAccountBalanceResponse200) o;
        return Objects.equals(account, that.account) &&
            Objects.equals(balances, that.balances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account,
            balances);
    }
}
