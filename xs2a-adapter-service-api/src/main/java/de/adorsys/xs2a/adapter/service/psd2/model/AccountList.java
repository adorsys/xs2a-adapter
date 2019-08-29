package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.List;

public class AccountList {
    private List<AccountDetails> accounts;

    public List<AccountDetails> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDetails> accounts) {
        this.accounts = accounts;
    }
}
