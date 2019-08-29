package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.List;

public class ReadAccountBalanceResponse {
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
}
