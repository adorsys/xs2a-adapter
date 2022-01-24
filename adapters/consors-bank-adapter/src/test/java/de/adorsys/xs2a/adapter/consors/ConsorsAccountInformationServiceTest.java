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

package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.*;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConsorsAccountInformationServiceTest {

    private final HttpClient httpClient = Mockito.spy(AbstractHttpClient.class);
    private final HttpClientConfig httpClientConfig = Mockito.mock(HttpClientConfig.class);
    private final AccountInformationService service = new ConsorsServiceProvider()
        .getAccountInformationService(new Aspsp(), getHttpClientFactory(), new IdentityLinksRewriter());
    private ArgumentCaptor<Request.Builder> builderCaptor;

    private HttpClientFactory getHttpClientFactory() {
        return new HttpClientFactory() {
            @Override
            public HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites) {
                return httpClient;
            }

            @Override
            public HttpClientConfig getHttpClientConfig() {
                return httpClientConfig;
            }
        };
    }

    @BeforeEach
    void setUp() {
        builderCaptor = ArgumentCaptor.forClass(Request.Builder.class);
    }

    @Test
    void createConsent_noPsuId() {
        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.empty(), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, "");
    }

    @Test
    void createConsent_blankPsuId() {
        Map<String, String> headers = new HashMap<>();
        headers.put(PSU_ID, " ");

        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.fromMap(headers), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, "");
    }

    @Test
    void createConsent_notEmptyPsuId() {
        Map<String, String> headers = new HashMap<>();
        headers.put(PSU_ID, "foo");

        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.fromMap(headers), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, "");
    }

    @Test
    void getTransactionSetsAcceptApplicationJson() {
        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(200, new TransactionsResponse200Json(), ResponseHeaders.emptyResponseHeaders()));

        service.getTransactionListAsString(null, RequestHeaders.empty(), RequestParams.empty());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());
        assertThat(builderCaptor.getValue().headers())
            .containsEntry(RequestHeaders.ACCEPT, ContentType.APPLICATION_JSON);
    }

    @Test
    void getTransactionDetails() {
        String rawResponse = "{\n" +
            "  \"transactionsDetails\": {\n" +
            "    \"transactionId\": \"test\"\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", "https://uri.com"));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(-1,
                    responseHandler.apply(200,
                        new ByteArrayInputStream(rawResponse.getBytes()),
                        ResponseHeaders.emptyResponseHeaders()),
                    null);
            });

        Response<?> actualResponse
            = service.getTransactionDetails("accountId", "transactionId", RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(OK200TransactionDetails.class))
            .matches(body ->
                body.getTransactionsDetails()
                    .getTransactionId()
                    .equals("test"));
    }
}
