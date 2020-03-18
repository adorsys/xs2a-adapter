package de.adorsys.xs2a.adapter.model;

import javax.annotation.Generated;
import java.util.List;

@Generated("xs2a-adapter-codegen")
public class ReadCardAccountBalanceResponse200TO {
    private AccountReferenceTO cardAccount;

    private List<BalanceTO> balances;

    public AccountReferenceTO getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(AccountReferenceTO cardAccount) {
        this.cardAccount = cardAccount;
    }

    public List<BalanceTO> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceTO> balances) {
        this.balances = balances;
    }
}
