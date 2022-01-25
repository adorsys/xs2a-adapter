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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class IngAccountInformationApiTest {

    private static final String RESOURCE_ID = "resourceId";
    private static final String BALANCES = "%s/v3/accounts/%s/balances";
    private static final String CARD_ACCOUNTS = "%s/v1/card-accounts/%s/transactions";
    private static final String BASE_URI = "https://base.uri";
    private static final String REQUEST_ID = "requestId";

    private IngAccountInformationApi accountInformationApi;
    private final HttpClient httpClient = mock(HttpClient.class);
    private final Request.Builder builder = mock(Request.Builder.class);

    private final ArgumentCaptor<String> uriCaptor = ArgumentCaptor.forClass(String.class);

    @BeforeEach
    void setUp() {
        accountInformationApi = new IngAccountInformationApi(BASE_URI, httpClient, null);

        when(httpClient.get(anyString())).thenReturn(builder);
        when(builder.header(any(), any())).thenReturn(builder);
    }

    @Test
    void getBalances() {
        String expectedUri = String.format(BALANCES, BASE_URI, RESOURCE_ID);

        accountInformationApi.getBalances(RESOURCE_ID, emptyList(), null, REQUEST_ID, emptyList());

        verifyInvocations();

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);
    }

    @Test
    void getCardAccountTransactions() {
        String expectedUri = String.format(CARD_ACCOUNTS, BASE_URI, RESOURCE_ID);

        accountInformationApi.getCardAccountTransactions(RESOURCE_ID, null, null, null, REQUEST_ID, emptyList());

        verifyInvocations();

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);
    }

    private void verifyInvocations() {
        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(builder, times(1)).header(anyString(), anyString());
        verify(builder, times(1)).send(any(), anyList());
    }
}
