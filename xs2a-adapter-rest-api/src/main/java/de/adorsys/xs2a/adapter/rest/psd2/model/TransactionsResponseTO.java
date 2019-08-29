package de.adorsys.xs2a.adapter.rest.psd2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.Map;

@Generated("xs2a-codegen")
public class TransactionsResponseTO {
    private AccountReferenceTO account;

    private AccountReportTO transactions;

    private List<BalanceTO> balances;

    @JsonProperty("_links")
    private Map<String, HrefTypeTO> links;

    public AccountReferenceTO getAccount() {
        return account;
    }

    public void setAccount(AccountReferenceTO account) {
        this.account = account;
    }

    public AccountReportTO getTransactions() {
        return transactions;
    }

    public void setTransactions(AccountReportTO transactions) {
        this.transactions = transactions;
    }

    public List<BalanceTO> getBalances() {
        return balances;
    }

    public void setBalances(List<BalanceTO> balances) {
        this.balances = balances;
    }

    public Map<String, HrefTypeTO> getLinks() {
        return links;
    }

    public void setLinks(Map<String, HrefTypeTO> links) {
        this.links = links;
    }
}
