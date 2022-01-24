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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AbstractAdapterServiceProviderTest {

    private AbstractAdapterServiceProvider provider;
    private Aspsp aspsp;

    @BeforeEach
    void setUp() {
        provider = new AbstractAdapterServiceProvider() {
            @Override
            public AccountInformationService getAccountInformationService(Aspsp aspsp, HttpClientFactory httpClientFactory, LinksRewriter linksRewriter) {
                return null;
            }

            @Override
            public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp, HttpClientFactory httpClientFactory, LinksRewriter linksRewriter) {
                return null;
            }

            @Override
            public String getAdapterId() {
                return null;
            }
        };
        aspsp = new Aspsp();
    }

    @Test
    void getInterceptors_wiremockValidationDisabled_InterceptorIsNull() {
        provider.wiremockValidationEnabled(false);
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors).isEqualTo(Collections.emptyList());
    }

    @Test
    void getInterceptors_wiremockValidationDisabled_InterceptorIsNotNull() {
        provider.wiremockValidationEnabled(false);
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, builder -> null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors.size()).isEqualTo(1);
    }

    @Test
    void getInterceptors_wiremockValidationEnabled_InterceptorIsNotNull_wrongAdapterId() {
        provider.wiremockValidationEnabled(true);
        aspsp.setAdapterId("wrong-adapter-id");
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, builder -> null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors.size()).isEqualTo(1);
    }

    @Test
    void getInterceptors_wiremockValidationEnabled_InterceptorIsNotNull_WiremockStubInterceptor() {
        provider.wiremockValidationEnabled(true);
        aspsp.setAdapterId("adorsys-adapter");
        List<Interceptor> interceptors = provider.getInterceptors(aspsp, builder -> null);
        assertThat(interceptors).isNotNull();
        assertThat(interceptors.size()).isEqualTo(2);
    }
}
