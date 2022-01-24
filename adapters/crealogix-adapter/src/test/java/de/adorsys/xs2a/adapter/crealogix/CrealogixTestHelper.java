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

package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;

import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.mock;

public class CrealogixTestHelper {

    protected static final String AUTHORIZATION = "Authorization";
    protected static final String TOKEN = "token";
    protected static final String AUTHORIZATION_VALUE = "Bearer " + TOKEN;
    protected final HttpClient httpClient = mock(HttpClient.class);
    protected final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    protected final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    protected final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    protected final Aspsp aspsp = getAspsp();

    protected  <T> Response<T> getResponse(T body) {
        return new Response<>(-1, body, ResponseHeaders.emptyResponseHeaders());
    }

    protected Request.Builder getRequestBuilder(String method) {
        return new RequestBuilderImpl(httpClient, method, "");
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }

    protected RequestHeaders getHeadersWithAuthorization() {
        return getHeadersWithAuthorization(AUTHORIZATION_VALUE);
    }

    protected RequestHeaders getHeadersWithAuthorization(String value) {
        return RequestHeaders.fromMap(singletonMap(AUTHORIZATION, value));
    }
}
