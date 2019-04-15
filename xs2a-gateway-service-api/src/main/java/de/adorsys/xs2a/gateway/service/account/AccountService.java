package de.adorsys.xs2a.gateway.service.account;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;

public interface AccountService {
    AccountListHolder getAccountList(Headers headers, RequestParams requestParams);
}
