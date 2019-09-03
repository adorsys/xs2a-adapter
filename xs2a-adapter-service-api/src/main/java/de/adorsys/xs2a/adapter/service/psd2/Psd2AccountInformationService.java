package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.psd2.model.AccountList;
import de.adorsys.xs2a.adapter.service.psd2.model.ReadAccountBalanceResponse;

import java.io.IOException;
import java.util.Map;

public interface Psd2AccountInformationService extends Psd2ConsentService, Oauth2Service {

    AccountList getAccounts(Map<String, String> queryParameters, Map<String, String> headers) throws IOException;

    ReadAccountBalanceResponse getBalances(String accountId,
                              Map<String, String> queryParameters,
                              Map<String, String> headers) throws IOException;

    /**
     * @return TransactionsResponse or String for xml response
     */
    Object getTransactions(String accountId,
                                       Map<String, String> queryParameters,
                                       Map<String, String> headers) throws IOException;
}
