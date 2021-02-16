package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.ing.model.IngBalancesResponse;
import de.adorsys.xs2a.adapter.ing.model.IngTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngAccountInformationServiceTest {

    private static final String CONSENT_ID = "consentId";
    private static final String AUTHORISATION_ID = "authorisationId";
    private static final String ACCOUNT_ID = "accountId";

    @InjectMocks
    private IngAccountInformationService accountInformationService;

    @Mock
    private IngAccountInformationApi accountInformationApi;
    @Mock
    private IngOauth2Service oauth2Service;
    @Mock
    private LinksRewriter linksRewriter;

    private static final RequestHeaders emptyRequestHeaders = RequestHeaders.empty();
    private static final RequestParams emptyParams = RequestParams.empty();
    private static final ResponseHeaders emptyResponseHeaders = ResponseHeaders.emptyResponseHeaders();

    @Test
    void createConsent() {
        Response<ConsentsResponse201> actualResponse = accountInformationService.createConsent(emptyRequestHeaders, emptyParams, null);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getHeaders().getHeadersMap().isEmpty())
            .matches(r -> r.getStatusCode() == 200)
            .matches(r -> r.getBody().equals(new ConsentsResponse201()));
    }

    @Test
    void getConsentInformation() {
        assertThatThrownBy(() -> accountInformationService.getConsentInformation(CONSENT_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void deleteConsent() {
        assertThatThrownBy(() -> accountInformationService.deleteConsent(CONSENT_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getConsentStatus() {
        assertThatThrownBy(() -> accountInformationService.getConsentStatus(CONSENT_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void startConsentAuthorisation() {
        assertThatThrownBy(() -> accountInformationService.startConsentAuthorisation(CONSENT_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void startConsentAuthorisation_withPsuAuthentication() {
        assertThatThrownBy(() -> accountInformationService.startConsentAuthorisation(CONSENT_ID, emptyRequestHeaders, emptyParams, null))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void updateConsentsPsuData_selectScaMethod() {
        SelectPsuAuthenticationMethod body = new SelectPsuAuthenticationMethod();
        assertThatThrownBy(() -> accountInformationService
            .updateConsentsPsuData(CONSENT_ID, AUTHORISATION_ID, emptyRequestHeaders, emptyParams, body))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void updateConsentsPsuData_authoriseTransaction() {
        TransactionAuthorisation body = new TransactionAuthorisation();
        assertThatThrownBy(() -> accountInformationService
            .updateConsentsPsuData(CONSENT_ID, AUTHORISATION_ID, emptyRequestHeaders, emptyParams, body))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void updateConsentsPsuData_updatePsuAuthentication() {
        UpdatePsuAuthentication body = new UpdatePsuAuthentication();
        assertThatThrownBy(() -> accountInformationService
            .updateConsentsPsuData(CONSENT_ID, AUTHORISATION_ID, emptyRequestHeaders, emptyParams, body))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getConsentScaStatus() {
        assertThatThrownBy(() -> accountInformationService
            .getConsentScaStatus(CONSENT_ID, AUTHORISATION_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getCardAccountList() {
        assertThatThrownBy(() -> accountInformationService.getCardAccountList(emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getCardAccountDetails() {
        assertThatThrownBy(() -> accountInformationService.getCardAccountDetails(ACCOUNT_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getCardAccountBalances() {
        assertThatThrownBy(() -> accountInformationService.getCardAccountBalances(ACCOUNT_ID, emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getCardAccountTransactionList() {
        CardAccountsTransactionsResponse200 expectedBody = new CardAccountsTransactionsResponse200();
        expectedBody.setLinks(Collections.emptyMap());

        when(accountInformationApi.getCardAccountTransactions(anyString(), any(), any(), any(), any(), anyList()))
            .thenReturn(getResponse(expectedBody));

        Response<CardAccountsTransactionsResponse200> actualResponse
            = accountInformationService.getCardAccountTransactionList(ACCOUNT_ID, emptyRequestHeaders, emptyParams);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getBody().equals(expectedBody))
            .matches(r -> r.getStatusCode() == 200)
            .matches(r -> r.getHeaders().getHeadersMap().isEmpty());
    }

    @Test
    void getBalances() {
        when(accountInformationApi.getBalances(anyString(), any(), any(), any(), anyList()))
            .thenReturn(getResponse(new IngBalancesResponse()));

        Response<ReadAccountBalanceResponse200> actualResponse
            = accountInformationService.getBalances(ACCOUNT_ID, emptyRequestHeaders, emptyParams);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getBody().equals(new ReadAccountBalanceResponse200()))
            .matches(r -> r.getStatusCode() == 200)
            .matches(r -> r.getHeaders().getHeadersMap().isEmpty());
    }

    @Test
    void getTransactionDetails() {
        assertThatThrownBy(() -> accountInformationService
            .getTransactionDetails(ACCOUNT_ID, "transactionId", emptyRequestHeaders, emptyParams))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getTransactionListAsString() {
        when(accountInformationApi.getTransactions(anyString(), any(), any(), any(), any(), any(), anyList()))
            .thenReturn(getResponse(new IngTransactionsResponse()));

        Response<String> actualResponse
            = accountInformationService.getTransactionListAsString(ACCOUNT_ID, emptyRequestHeaders, emptyParams);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getBody().equals(getTransactionsBodyAsString()))
            .matches(r -> r.getStatusCode() == 200)
            .matches(r -> r.getHeaders().getHeadersMap().isEmpty());
    }

    private <T> Response<T> getResponse(T body) {
        return new Response<>(200, body, emptyResponseHeaders);
    }

    private String getTransactionsBodyAsString() {
        TransactionsResponse200Json body = new TransactionsResponse200Json();
        body.setLinks(Collections.emptyMap());

        return new JacksonObjectMapper().writeValueAsString(body);
    }
}
