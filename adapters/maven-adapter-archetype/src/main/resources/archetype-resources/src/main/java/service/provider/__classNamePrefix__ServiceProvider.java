#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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

package ${package}.service.provider;

import ${package}.adapter.BaseAccountInformationService;
import ${package}.adapter.BasePaymentInitiationService;
import ${package}.http.HttpClientFactory;
import ${package}.service.PaymentInitiationService;
import ${package}.service.AccountInformationService;
import ${package}.service.Pkcs12KeyStore;
import ${package}.service.model.Aspsp;

public class ${classNamePrefix}ServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return new BaseAccountInformationService(aspsp, httpClientFactory.getHttpClient(getAdapterId()));
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return new BasePaymentInitiationService(baseUrl, httpClientFactory.getHttpClient(getAdapterId()));
    }

    @Override
    public String getAdapterId() {
        return "${artifactId}";
    }
}
