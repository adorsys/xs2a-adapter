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

package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;

public class DeutscheBankServiceProvider extends AbstractAdapterServiceProvider implements DownloadServiceProvider {
    public static final String SERVICE_GROUP_PLACEHOLDER = "{Service Group}";
    private final PsuIdTypeHeaderInterceptor psuIdTypeHeaderInterceptor = new PsuIdTypeHeaderInterceptor();
    private final PsuIdHeaderInterceptor psuIdHeaderInterceptor = new PsuIdHeaderInterceptor();

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        aspsp.setUrl(aspsp.getUrl().replace(SERVICE_GROUP_PLACEHOLDER, "ais"));
        return new DeutscheBankAccountInformationService(aspsp,
            httpClientFactory,
            getInterceptors(aspsp, psuIdTypeHeaderInterceptor, psuIdHeaderInterceptor),
            linksRewriter,
            new DeutscheBankPsuPasswordEncryptionService());
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        aspsp.setUrl(aspsp.getUrl().replace(SERVICE_GROUP_PLACEHOLDER, "pis"));
        return new DeutscheBankPaymentInitiationService(aspsp,
            httpClientFactory,
            getInterceptors(aspsp, psuIdTypeHeaderInterceptor, psuIdHeaderInterceptor),
            linksRewriter);
    }

    @Override
    public DownloadService getDownloadService(String baseUrl,
                                              HttpClientFactory httpClientFactory) {
        return new BaseDownloadService(baseUrl,
            httpClientFactory.getHttpClient(getAdapterId()),
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public String getAdapterId() {
        return "deutsche-bank-adapter";
    }
}
