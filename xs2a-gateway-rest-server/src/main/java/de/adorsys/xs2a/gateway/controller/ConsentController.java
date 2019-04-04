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

package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.api.ConsentApi;
import de.adorsys.xs2a.gateway.model.ais.Consents;
import de.adorsys.xs2a.gateway.model.ais.ConsentsResponse201;
import de.adorsys.xs2a.gateway.service.ConsentCreationHeaders;
import de.adorsys.xs2a.gateway.service.consent.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.consent.ConsentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ConsentController implements ConsentApi {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @Override
    public ResponseEntity<ConsentsResponse201> createConsent(String bankCode, UUID xRequestID, Consents body, String digest, String signature, byte[] tpPSignatureCertificate, String psuId, String psUIDType, String psUCorporateID, String psUCorporateIDType, boolean tpPRedirectPreferred, String tpPRedirectURI, String tpPNokRedirectURI, boolean tpPExplicitAuthorisationPreferred, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        ConsentCreationHeaders headers = ConsentCreationHeaders.builder()
                                                 .bankCode(bankCode)
                                                 .xRequestID(xRequestID)
                                                 .digest(digest)
                                                 .signature(signature)
                                                 .tppSignatureCertificate(tpPSignatureCertificate)
                                                 .psuId(psuId)
                                                 .psUIDType(psUIDType)
                                                 .psUCorporateID(psUCorporateID)
                                                 .psUCorporateIDType(psUCorporateIDType)
                                                 .tpPRedirectPreferred(tpPRedirectPreferred)
                                                 .tpPRedirectURI(tpPRedirectURI)
                                                 .tpPNokRedirectURI(tpPNokRedirectURI)
                                                 .tpPExplicitAuthorisationPreferred(tpPExplicitAuthorisationPreferred)
                                                 .psUIPAddress(psUIPAddress)
                                                 .psUIPPort(psUIPPort)
                                                 .psUAccept(psUAccept)
                                                 .psUAcceptCharset(psUAcceptCharset)
                                                 .psUAcceptEncoding(psUAcceptEncoding)
                                                 .psUAcceptLanguage(psUAcceptLanguage)
                                                 .psUUserAgent(psUUserAgent)
                                                 .psUHttpMethod(psUHttpMethod)
                                                 .psUDeviceID(psUDeviceID)
                                                 .psUGeoLocation(psUGeoLocation)
                                                 .build();
        //todo: convert body to api model
        ConsentCreationResponse consent = consentService.createConsent(body, headers);
        return null;
    }

}
