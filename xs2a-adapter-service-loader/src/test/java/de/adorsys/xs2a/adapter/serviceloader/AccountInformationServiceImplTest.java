/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountInformationServiceImplTest {
    private static final int HTTP_CODE_200 = 200;
    private static final RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());
    private static final RequestParams parameters = RequestParams.empty();
    private static final String CONSENT_ID = "consentId";
    private static final String AUTHORISATION_ID = "authorisationId";
    private static final String ACCOUNT_ID = "accountId";
    private static final String TRANSACTION_ID = "transactionId";

    @InjectMocks
    private AccountInformationServiceImpl service;

    @Mock
    private AccountInformationService accountInformationService;
    @Mock
    private AdapterServiceLoader adapterServiceLoader;

    @BeforeEach
    void setUp() {
        when(adapterServiceLoader.getAccountInformationService(any(RequestHeaders.class)))
            .thenReturn(accountInformationService);
    }

    @Test
    void createConsent() {
        Response<ConsentsResponse201> response = new Response<>(HTTP_CODE_200,
            new ConsentsResponse201(),
            ResponseHeaders.fromMap(Collections.emptyMap()));

        when(accountInformationService.createConsent(any(), any(), any())).thenReturn(response);

        Response<ConsentsResponse201> consentResponse =
            service.createConsent(RequestHeaders.fromMap(Collections.singletonMap(RequestHeaders.X_GTW_ASPSP_ID, "BIC")),
                RequestParams.empty(),
                new Consents());

        verify(accountInformationService, times(1)).createConsent(any(), any(), any());

        assertThat(consentResponse).isEqualTo(response);
    }

    @Test
    void getConsentInformation() {
        service.getConsentInformation(CONSENT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getConsentInformation(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void deleteConsent() {
        service.deleteConsent(CONSENT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .deleteConsent(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getConsentStatus() {
        service.getConsentStatus(CONSENT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getConsentStatus(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getConsentAuthorisation() {
        service.getConsentAuthorisation(CONSENT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getConsentAuthorisation(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void startConsentAuthorisation() {
        service.startConsentAuthorisation(CONSENT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .startConsentAuthorisation(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void startConsentAuthorisation_updatePsuAuthentication() {
        service.startConsentAuthorisation(CONSENT_ID, headers, parameters, new UpdatePsuAuthentication());

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .startConsentAuthorisation(anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(UpdatePsuAuthentication.class));
    }

    @Test
    void updateConsentsPsuData_selectPsuAuthenticationMethod() {
        service.updateConsentsPsuData(CONSENT_ID, AUTHORISATION_ID, headers, parameters, new SelectPsuAuthenticationMethod());

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .updateConsentsPsuData(anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(SelectPsuAuthenticationMethod.class));
    }

    @Test
    void updateConsentsPsuData_transactionAuthorisation() {
        service.updateConsentsPsuData(CONSENT_ID, AUTHORISATION_ID, headers, parameters, new TransactionAuthorisation());

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .updateConsentsPsuData(anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(TransactionAuthorisation.class));
    }

    @Test
    void updateConsentsPsuData_updatePsuAuthentication() {
        service.updateConsentsPsuData(CONSENT_ID, AUTHORISATION_ID, headers, parameters, new UpdatePsuAuthentication());

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .updateConsentsPsuData(anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(UpdatePsuAuthentication.class));
    }

    @Test
    void getAccountList() {
        service.getAccountList(headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getAccountList(any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void readAccountDetails() {
        service.readAccountDetails(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .readAccountDetails(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getTransactionList() {
        service.getTransactionList(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getTransactionList(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getTransactionDetails() {
        service.getTransactionDetails(ACCOUNT_ID, TRANSACTION_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getTransactionDetails(anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getTransactionListAsString() {
        service.getTransactionListAsString(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getTransactionListAsString(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getConsentScaStatus() {
        service.getConsentScaStatus(CONSENT_ID, AUTHORISATION_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getConsentScaStatus(anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getBalances() {
        service.getBalances(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getBalances(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getCardAccountList() {
        service.getCardAccountList(headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getCardAccountList(any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getCardAccountDetails() {
        service.getCardAccountDetails(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getCardAccountDetails(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getCardAccountBalances() {
        service.getCardAccountBalances(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getCardAccountBalances(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getCardAccountTransactionList() {
        service.getCardAccountTransactionList(ACCOUNT_ID, headers, parameters);

        verify(adapterServiceLoader, times(1))
            .getAccountInformationService(any(RequestHeaders.class));
        verify(accountInformationService, times(1))
            .getCardAccountTransactionList(anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }
}
