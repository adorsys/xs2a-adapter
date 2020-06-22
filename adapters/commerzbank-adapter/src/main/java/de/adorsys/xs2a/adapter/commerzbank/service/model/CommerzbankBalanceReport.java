package de.adorsys.xs2a.adapter.commerzbank.service.model;

import de.adorsys.xs2a.adapter.api.model.AccountReference;

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
