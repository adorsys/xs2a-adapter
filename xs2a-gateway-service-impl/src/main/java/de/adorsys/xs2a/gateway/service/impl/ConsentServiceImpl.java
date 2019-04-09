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

package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.consent.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.consent.ConsentService;
import de.adorsys.xs2a.gateway.service.consent.Consents;

public class ConsentServiceImpl implements ConsentService {

    private ConsentService service = new DeutscheBankConsentService();

    void setConsentService(ConsentService service) {
        this.service = service;
    }

    @Override
    public ConsentCreationResponse createConsent(Consents consents, Headers headers) {
        //todo: user bank registry to define bank
        return service.createConsent(consents, headers);
    }
}
