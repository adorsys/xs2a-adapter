package de.adorsys.xs2a.adapter.service.model;

import java.util.List;

public class ComdirectBalanceReport {
    private List<ComdirectBalance> balances;
    private AccountReference account;

    public List<ComdirectBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<ComdirectBalance> balances) {
        this.balances = balances;
    }

    public AccountReference getAccount() {
        return account;
    }

    public void setAccount(AccountReference account) {
        this.account = account;
    }
}
