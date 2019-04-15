package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.AccountService;

public class AccountServiceImpl implements AccountService {
    private AccountService accountService = new DeutscheBankAccountService();

    @Override
    public AccountListHolder getAccountList(Headers headers, RequestParams requestParams) {
        return accountService.getAccountList(headers, requestParams);
    }
}
