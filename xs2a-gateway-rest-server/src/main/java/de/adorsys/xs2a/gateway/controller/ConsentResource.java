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
import de.adorsys.xs2a.gateway.mapper.ConsentCreationResponseMapper;
import de.adorsys.xs2a.gateway.mapper.ConsentMapper;
import de.adorsys.xs2a.gateway.model.ais.ConsentsTO;
import de.adorsys.xs2a.gateway.model.ais.ConsentsResponse201;
import de.adorsys.xs2a.gateway.service.ConsentCreationHeaders;
import de.adorsys.xs2a.gateway.service.consent.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.consent.ConsentService;
import de.adorsys.xs2a.gateway.service.consent.Consents;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ConsentResource implements ConsentApi {

    private final ConsentService consentService;

    private final ConsentMapper consentMapper = Mappers.getMapper(ConsentMapper.class);

    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);

    public ConsentResource(ConsentService consentService) {
        this.consentService = consentService;
    }

    @Override
    public ResponseEntity<ConsentsResponse201> createConsent(String bankCode, UUID xRequestID, ConsentsTO body, String digest, String signature, byte[] tpPSignatureCertificate, String psuId, String psUIDType, String psUCorporateID, String psUCorporateIDType, boolean tpPRedirectPreferred, String tpPRedirectURI, String tpPNokRedirectURI, boolean tpPExplicitAuthorisationPreferred, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
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
        Consents consents = consentMapper.toConsents(body);
        ConsentCreationResponse consent = consentService.createConsent(consents, headers);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponseMapper.toConsentResponse201(consent));
    }

}
