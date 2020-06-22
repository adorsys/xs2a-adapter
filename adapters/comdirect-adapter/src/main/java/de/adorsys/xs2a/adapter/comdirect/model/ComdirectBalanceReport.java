package de.adorsys.xs2a.adapter.comdirect.model;

import de.adorsys.xs2a.adapter.api.model.AccountReference;

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
