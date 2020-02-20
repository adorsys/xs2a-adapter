package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.io.IOException;
import java.util.Map;

/**
 * @deprecated
 * This service is no longer acceptable and will be removed in future releases.
 * <p>Use {@link de.adorsys.xs2a.adapter.service.AccountInformationService} instead.</p>
 */
@Deprecated
public interface Psd2AccountInformationService {

    Response<AccountList> getAccounts(Map<String, String> queryParameters,
                                      Map<String, String> headers) throws IOException;

    Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                     Map<String, String> queryParameters,
                                                     Map<String, String> headers) throws IOException;

    /**
     * @return TransactionsResponse or String for xml response
     */
    Response getTransactions(String accountId,
                             Map<String, String> queryParameters,
                             Map<String, String> headers) throws IOException;

    Response<ConsentsResponse> createConsent(Map<String, String> queryParameters,
                                             Map<String, String> headers,
                                             Consents consents);

    Response<ConsentInformationResponse> getConsentInformation(String consentId,
                                                               Map<String, String> queryParameters,
                                                               Map<String, String> headers);

    Response<Void> deleteConsent(String consentId,
                                 Map<String, String> queryParameters,
                                 Map<String, String> headers);

    Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                     Map<String, String> queryParameters,
                                                     Map<String, String> headers);

    Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                Map<String, String> queryParameters,
                                                                Map<String, String> headers,
                                                                UpdateAuthorisation updateAuthentication);

    Response<UpdateAuthorisationResponse> updateConsentsPsuData(String consentId,
                                                                String authorisationId,
                                                                Map<String, String> queryParameters,
                                                                Map<String, String> headers,
                                                                UpdateAuthorisation updateAuthentication);

    Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                    String authorisationId,
                                                    Map<String, String> headers);
}
