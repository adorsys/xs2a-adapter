package de.adorsys.xs2a.adapter.service.model;

import java.util.List;

public class CardAccountBalanceReport {
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
}
