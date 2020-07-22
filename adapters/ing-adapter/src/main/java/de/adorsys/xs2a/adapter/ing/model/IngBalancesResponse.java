package de.adorsys.xs2a.adapter.ing.model;

import java.util.List;

public class IngBalancesResponse {
    private IngAccountReferenceIban account;

    private List<IngBalance> balances;

    public IngAccountReferenceIban getAccount() {
        return account;
    }

    public void setAccount(IngAccountReferenceIban account) {
        this.account = account;
    }

    public List<IngBalance> getBalances() {
        return balances;
    }

    public void setBalances(List<IngBalance> balances) {
        this.balances = balances;
    }
}
