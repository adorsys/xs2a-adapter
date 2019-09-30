/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.commerzbank.service.provider;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.commerzbank.service.CommerzbankAccountInformationService;
import de.adorsys.xs2a.adapter.commerzbank.service.CommerzbankOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;

public class CommerzbankServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceFactory {

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl, HttpClient httpClient) {
        return new CommerzbankAccountInformationService(baseUrl, httpClient);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl, HttpClient httpClient) {
        return new BasePaymentInitiationService(baseUrl, httpClient);
    }

    @Override
    public String getAdapterId() {
        return "commerzbank-adapter";
    }

    @Override
    public Oauth2Service getOauth2Service(String baseUrl, Pkcs12KeyStore keyStore, HttpClient httpClient) {
        return new CommerzbankOauth2Service(baseUrl, httpClient);
    }
}
