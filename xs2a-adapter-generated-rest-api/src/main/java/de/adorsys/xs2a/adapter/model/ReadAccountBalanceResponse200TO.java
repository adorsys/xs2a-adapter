package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class ReadAccountBalanceResponse200TO {
    private AccountReferenceTO account;

    private List<BalanceTO> balances;

    public AccountReferenceTO getAccount() {
        return account;
    }

    public void setAccount(AccountReferenceTO account) {
        this.account = account;
    }

    public List<BalanceTO> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceTO> balances) {
        this.balances = balances;
    }
}
