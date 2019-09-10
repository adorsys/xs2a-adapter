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

package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.service.*;

public class ComdirectServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceFactory {

    private static final String BERLIN_GROUP_CONTEXT_PATH = "/berlingroup";

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl) {
        return new ComdirectAccountInformationService(wrapBaseUrl(baseUrl));
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl) {
        return new BasePaymentInitiationService(wrapBaseUrl(baseUrl));
    }

    @Override
    public String getAdapterId() {
        return "comdirect-adapter";
    }

    @Override
    public Oauth2Service getOauth2Service(String baseUrl, Pkcs12KeyStore keyStore) {
        return new ComdirectOauth2Service(wrapBaseUrl(baseUrl));
    }

    private static String wrapBaseUrl(String baseUrl) {
        return baseUrl + BERLIN_GROUP_CONTEXT_PATH;
    }
}
