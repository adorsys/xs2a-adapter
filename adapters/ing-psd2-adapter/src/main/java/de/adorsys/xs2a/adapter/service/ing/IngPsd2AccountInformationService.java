package de.adorsys.xs2a.adapter.service.ing;

import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.ing.internal.api.*;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.*;
import java.security.cert.CertificateException;
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

    public IngPsd2AccountInformationService(String baseUri, Pkcs12KeyStore keyStore)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnrecoverableEntryException, IOException, InvalidKeyException {
        Host host = new Host(baseUri);
        SSLContext sslContext = keyStore.getSslContext();
        HttpTransport transport = new NetHttpTransport.Builder()
            .setSslSocketFactory(sslContext.getSocketFactory())
            .build();
        JacksonAnnotationAwareJsonParser parser = new JacksonAnnotationAwareJsonParser();
        accountInformationApi = new AccountInformationApi(host, transport, parser);
        Oauth2Api oauth2Api = new Oauth2Api(host, transport, parser);

        ClientAuthenticationFactory clientAuthenticationFactory = new ClientAuthenticationFactory(keyStore.getQsealCertificate(), keyStore.getQsealPrivateKey());
        oauth2Service = new IngOauth2Service(oauth2Api, clientAuthenticationFactory);
    }

    @Override
    public Response<ConsentsResponse> createConsent(Map<String, String> headers, Consents consents) {
        return toResponse(new ConsentsResponse());
    }

    private <T> Response<T> toResponse(T body) {
        return new Response<>(200, body, ResponseHeaders.fromMap(emptyMap()));
    }

    @Override
    public Response<ConsentInformationResponse> getConsentInformation(String consentId, Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<Void> deleteConsent(String consentId, Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ConsentStatusResponse> getConsentStatus(String consentId, Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdateAuthorisationResponse> updateConsentsPsuData(String consentId,
                                                                       String authorisationId,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, String state, URI redirectUri) throws IOException {
        try {
            return oauth2Service.getAuthorizationRequestUri(state, redirectUri);
        } catch (HttpResponseException e) {
            throw new ErrorResponseException(e.getStatusCode(), ResponseHeaders.emptyResponseHeaders());
        }
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers,
                                  String authorizationCode,
                                  URI redirectUri,
                                  String clientId) throws IOException {
        try {
            return mapper.map(oauth2Service.getToken(authorizationCode));
        } catch (HttpResponseException e) {
            throw new ErrorResponseException(e.getStatusCode(), ResponseHeaders.emptyResponseHeaders());
        }
    }

    @Override
    public Response<AccountList> getAccounts(Map<String, String> queryParameters,
                                   Map<String, String> headers) throws IOException {
        return toResponse(getAccounts(new Headers(headers)));
    }

    private AccountList getAccounts(Headers headers) throws IOException {
        String accessToken = headers.getAccessToken();
        try {
            return mapper.map(accountInformationApi.getAccounts(oauth2Service.getClientAuthentication(accessToken)));
        } catch (HttpResponseException e) {
            throw new ErrorResponseException(e.getStatusCode(), ResponseHeaders.emptyResponseHeaders());
        }
    }

    @Override
    public Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                            Map<String, String> queryParameters,
                                                            Map<String, String> headers) throws IOException {
        return toResponse(
            getBalances(accountId, new QueryParameters(queryParameters), new Headers(headers))
        );
    }

    private ReadAccountBalanceResponse getBalances(String accountId,
                                                   QueryParameters queryParameters,
                                                   Headers headers) throws IOException {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        Currency currency = queryParameters.getCurrency();
        List<String> balanceTypes =
            queryParameters.get("balanceTypes", this::parseBalanceTypes);
        try {
            return mapper.map(accountInformationApi.getBalances(accountId, balanceTypes, currency, clientAuthentication));
        } catch (HttpResponseException e) {
            throw new ErrorResponseException(e.getStatusCode(), ResponseHeaders.emptyResponseHeaders());
        }
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
                                                          Map<String, String> headers) throws IOException {
        return toResponse(getTransactions(accountId, new QueryParameters(queryParameters), new Headers(headers)));
    }

    private TransactionsResponse getTransactions(String accountId,
                                                 QueryParameters queryParameters,
                                                 Headers headers) throws IOException {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        LocalDate dateFrom = queryParameters.getDateFrom();
        LocalDate dateTo = queryParameters.getDateTo();
        Currency currency = queryParameters.getCurrency();
        Integer limit = queryParameters.getLimit();
        try {
            return mapper.map(accountInformationApi.getTransactions(accountId, dateFrom, dateTo, currency, limit, clientAuthentication));
        } catch (HttpResponseException e) {
            throw new ErrorResponseException(e.getStatusCode(), ResponseHeaders.emptyResponseHeaders());
        }
    }
}
