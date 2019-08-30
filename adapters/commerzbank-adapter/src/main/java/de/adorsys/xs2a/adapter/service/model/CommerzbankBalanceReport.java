package de.adorsys.xs2a.adapter.service.model;

import java.util.List;

public class CommerzbankBalanceReport {
    private List<CommerzbankBalance> balances;
    private AccountReference account;

    public List<CommerzbankBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<CommerzbankBalance> balances) {
        this.balances = balances;
    }

    public AccountReference getAccount() {
        return account;
    }

    public void setAccount(AccountReference account) {
        this.account = account;
    }
}
