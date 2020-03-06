package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.ing.internal.api.AccountInformationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthentication;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthenticationFactory;
import de.adorsys.xs2a.adapter.service.ing.internal.api.Oauth2Api;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

public class IngPsd2AccountInformationService implements Psd2AccountInformationService, Oauth2Service {

    private final IngOauth2Service oauth2Service;
    private final AccountInformationApi accountInformationApi;
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);

    public IngPsd2AccountInformationService(String baseUri, HttpClient httpClient, Pkcs12KeyStore keyStore)
        throws GeneralSecurityException {

        accountInformationApi = new AccountInformationApi(baseUri, httpClient);
        Oauth2Api oauth2Api = new Oauth2Api(baseUri, httpClient);

        String qsealAlias = AdapterConfig.readProperty("ing.qseal.alias");
        X509Certificate qsealCertificate = keyStore.getQsealCertificate(qsealAlias);
        PrivateKey qsealPrivateKey = keyStore.getQsealPrivateKey(qsealAlias);
        ClientAuthenticationFactory clientAuthenticationFactory =
            new ClientAuthenticationFactory(qsealCertificate, qsealPrivateKey);
        oauth2Service = new IngOauth2Service(oauth2Api, clientAuthenticationFactory);
    }

    @Override
    public Response<ConsentsResponse> createConsent(Map<String, String> queryParameters,
                                                    Map<String, String> headers,
                                                    Consents consents) {
        return toResponse(new ConsentsResponse());
    }

    private <T> Response<T> toResponse(T body) {
        return new Response<>(200, body, ResponseHeaders.fromMap(emptyMap()));
    }

    @Override
    public Response<ConsentInformationResponse> getConsentInformation(String consentId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        Map<String, String> queryParameters,
                                        Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                            Map<String, String> queryParameters,
                                                            Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           Map<String, String> queryParameters,
                                                           Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdateAuthorisationResponse> updateConsentsPsuData(String consentId,
                                                                       String authorisationId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        return oauth2Service.getAuthorizationRequestUri(parameters);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        return mapper.map(oauth2Service.getToken(parameters));
    }

    @Override
    public Response<AccountList> getAccounts(Map<String, String> queryParameters, Map<String, String> headers) {
        return getAccounts(new Headers(headers));
    }

    private Response<AccountList> getAccounts(Headers headers) {
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(headers.getAccessToken());
        return accountInformationApi.getAccounts(headers.getRequestId(), clientAuthentication)
            .map(mapper::map);
    }

    @Override
    public Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                            Map<String, String> queryParameters,
                                                            Map<String, String> headers) {
        return getBalances(accountId, new QueryParameters(queryParameters), new Headers(headers));
    }

    private Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                             QueryParameters queryParameters,
                                                             Headers headers) {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        Currency currency = queryParameters.getCurrency();
        List<String> balanceTypes = queryParameters.get("balanceTypes", this::parseBalanceTypes);
        return accountInformationApi.getBalances(accountId,
            balanceTypes,
            currency,
            headers.getRequestId(),
            clientAuthentication)
            .map(mapper::map);
    }

    private List<String> parseBalanceTypes(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(value.split(","))
            .collect(Collectors.toList());
    }

    @Override
    public Response<TransactionsResponse> getTransactions(String accountId,
                                                          Map<String, String> queryParameters,
                                                          Map<String, String> headers) {
        return getTransactions(accountId, new QueryParameters(queryParameters), new Headers(headers));
    }

    private Response<TransactionsResponse> getTransactions(String accountId,
                                                           QueryParameters queryParameters,
                                                           Headers headers) {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        LocalDate dateFrom = queryParameters.getDateFrom();
        LocalDate dateTo = queryParameters.getDateTo();
        Currency currency = queryParameters.getCurrency();
        Integer limit = queryParameters.getLimit();
        return accountInformationApi.getTransactions(accountId,
            dateFrom,
            dateTo,
            currency,
            limit,
            headers.getRequestId(),
            clientAuthentication)
            .map(mapper::map);
    }
}
