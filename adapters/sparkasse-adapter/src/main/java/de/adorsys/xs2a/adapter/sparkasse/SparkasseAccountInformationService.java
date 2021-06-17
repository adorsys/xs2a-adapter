package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.sparkasse.model.*;
import org.mapstruct.factory.Mappers;

public class SparkasseAccountInformationService extends BaseAccountInformationService {

    private final SparkasseMapper sparkasseMapper = Mappers.getMapper(SparkasseMapper.class);

    public SparkasseAccountInformationService(Aspsp aspsp,
                                              HttpClientFactory httpClientFactory,
                                              LinksRewriter linksRewriter) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return super.createConsent(requestHeaders,
            requestParams,
            body,
            SparkasseConsentsResponse201.class,
            sparkasseMapper::toConsentsResponse201);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return super.startConsentAuthorisation(consentId,
            requestHeaders,
            requestParams,
            SparkasseStartScaprocessResponse.class,
            sparkasseMapper::toStartScaprocessResponse);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            SparkasseUpdatePsuAuthenticationResponse.class,
            sparkasseMapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod,
            SparkasseSelectPsuAuthenticationMethodResponse.class,
            sparkasseMapper::toSelectPsuAuthenticationMethodResponse);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
                                        requestHeaders,
                                        requestParams,
                                        SparkasseTransactionResponse200Json.class,
                                        sparkasseMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {

        return super.getTransactionDetails(accountId,
                                           transactionId,
                                           requestHeaders,
                                           requestParams,
                                           SparkasseOK200TransactionDetails.class,
                                           sparkasseMapper::toOK200TransactionDetails);
    }
}
