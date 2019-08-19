package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.io.IOException;
import java.util.Map;

public interface Psd2AccountInformationService extends Psd2ConsentService, Oauth2Service {

    AccountList getAccounts(Map<String, String> queryParameters, Map<String, String> headers) throws IOException;

    ReadAccountBalanceResponse getBalances(String accountId,
                              Map<String, String> queryParameters,
                              Map<String, String> headers) throws IOException;

    TransactionsResponse getTransactions(String accountId,
                                       Map<String, String> queryParameters,
                                       Map<String, String> headers) throws IOException;
}
