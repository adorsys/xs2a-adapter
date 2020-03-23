package de.adorsys.xs2a.adapter.service.psd2.model;

import java.util.List;

public class CardAccountList {
    private List<CardAccountDetails> cardAccounts;

    public List<CardAccountDetails> getCardAccounts() {
        return cardAccounts;
    }

    public void setCardAccounts(List<CardAccountDetails> cardAccounts) {
        this.cardAccounts = cardAccounts;
    }
}
