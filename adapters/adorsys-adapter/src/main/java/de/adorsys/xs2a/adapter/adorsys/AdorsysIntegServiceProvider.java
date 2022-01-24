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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.oauth2.api.BaseOauth2Api;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;

import java.util.ArrayList;
import java.util.List;

public class AdorsysIntegServiceProvider extends AbstractAdapterServiceProvider implements Oauth2ServiceProvider {

    private final OauthHeaderInterceptor oauthHeaderInterceptor = new OauthHeaderInterceptor();

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {

        HttpClientConfig config = httpClientFactory.getHttpClientConfig();
        return new BasePaymentInitiationService(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            getInterceptors(aspsp, getAdorsysInterceptors(config.getKeyStore())),
            linksRewriter,
            config.getLogSanitizer());
    }

    private Interceptor[] getAdorsysInterceptors(Pkcs12KeyStore keyStore) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(oauthHeaderInterceptor);
        if (keyStore != null) {
            interceptors.add(new AdorsysSigningHeadersInterceptor(keyStore));
        }
        return interceptors.toArray(new Interceptor[0]);
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        HttpClientConfig config = httpClientFactory.getHttpClientConfig();
        return new AdorsysAccountInformationService(aspsp,
                                                    httpClientFactory,
                                                    getInterceptors(aspsp, getAdorsysInterceptors(config.getKeyStore())),
                                                    linksRewriter);
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        HttpClientConfig config = httpClientFactory.getHttpClientConfig();
        return new AdorsysIntegOauth2Service(aspsp,
                                             httpClientFactory,
                                             new BaseOauth2Api<>(httpClient, AuthorisationServerMetaData.class, config.getLogSanitizer()));
    }

    @Override
    public String getAdapterId() {
        return "adorsys-adapter";
    }
}
