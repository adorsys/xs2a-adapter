package de.adorsys.xs2a.adapter.ing.model;

import java.util.List;

public class BalancesResponse {
    private AccountReferenceIban account;

    private List<Balance> balances;

    public AccountReferenceIban getAccount() {
        return account;
    }

    public void setAccount(AccountReferenceIban account) {
        this.account = account;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
}
