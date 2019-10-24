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

package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.ConsentsResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

class Psd2AccountInformationServiceInvocationHandler implements InvocationHandler {
    private Psd2AccountInformationService original;

    Psd2AccountInformationServiceInvocationHandler(Psd2AccountInformationService original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(original, args);
        if (method.getName().equals("createConsent") && AdapterConfig.isPsd2OauthModuleIntegrationEnabled()) {
            Map<String, String> headers = (Map<String, String>) args[0];
            return OAuthConsentResponseWrapper.wrap(headers, (Response<ConsentsResponse>) result);
        }
        return result;
    }
}
