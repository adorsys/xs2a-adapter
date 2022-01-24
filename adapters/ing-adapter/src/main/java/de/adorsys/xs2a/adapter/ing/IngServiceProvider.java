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

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;

public class IngServiceProvider extends AbstractAdapterServiceProvider implements Oauth2ServiceProvider {

    private static final LinksRewriter DEFAULT_LINKS_REWRITER = new IdentityLinksRewriter();

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        return getIngAccountInformationService(aspsp, httpClientFactory, linksRewriter);
    }

    private IngAccountInformationService getIngAccountInformationService(Aspsp aspsp,
                                                                         HttpClientFactory httpClientFactory,
                                                                         LinksRewriter linksRewriter) {
        return new IngAccountInformationService(IngApiFactory.getAccountInformationApi(aspsp, httpClientFactory),
            ingOauth2Service(aspsp, httpClientFactory),
            linksRewriter,
            getInterceptors(aspsp));

    }

    private IngOauth2Service ingOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        return new IngOauth2Service(IngApiFactory.getOAuth2Api(aspsp, httpClientFactory),
            IngClientAuthenticationFactory.getClientAuthenticationFactory(httpClientFactory.getHttpClientConfig().getKeyStore()),
            getInterceptors(aspsp));
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp,
                                          HttpClientFactory httpClientFactory) {
        return getIngAccountInformationService(aspsp,
            httpClientFactory,
            DEFAULT_LINKS_REWRITER);
    }

    @Override
    public String getAdapterId() {
        return "ing-adapter";
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        return new IngPaymentInitiationService(IngApiFactory.getPaymentInitiationApi(aspsp, httpClientFactory),
            ingOauth2Service(aspsp, httpClientFactory),
            linksRewriter,
            getInterceptors(aspsp));
    }
}
