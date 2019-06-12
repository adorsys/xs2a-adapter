package de.adorsys.xs2a.gateway.service.account;

import de.adorsys.xs2a.gateway.service.AccountReference;

import java.util.List;

public class BalanceReport {
    private List<Balance> balances;
    private AccountReference account;

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public AccountReference getAccount() {
        return account;
    }

    public void setAccount(AccountReference account) {
        this.account = account;
    }
}
