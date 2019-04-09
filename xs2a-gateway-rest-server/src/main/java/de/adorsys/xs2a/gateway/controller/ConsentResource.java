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
import de.adorsys.xs2a.gateway.model.ais.ConsentsResponse201;
import de.adorsys.xs2a.gateway.model.ais.ConsentsTO;
import de.adorsys.xs2a.gateway.service.Headers;
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
    public ResponseEntity<ConsentsResponse201> createConsent(String bankCode, UUID xRequestId, ConsentsTO body, String digest, String signature, byte[] tppSignatureCertificate, String psuId, String psuIdType, String psuCorporateId, String psuCorporateIdType, boolean tppRedirectPreferred, String tppRedirectUri, String tppNokRedirectUri, boolean tppExplicitAuthorisationPreferred, String psuIpAddress, String psuIpPort, String psuAccept, String psuAcceptCharset, String psuAcceptEncoding, String psuAcceptLanguage, String psuUserAgent, String psuHttpMethod, UUID psuDeviceId, String psuGeoLocation) {
        Headers headers = buildHeaders(bankCode, xRequestId, digest, signature, tppSignatureCertificate, psuId, psuIdType, psuCorporateId, psuCorporateIdType, tppRedirectPreferred, tppRedirectUri, tppNokRedirectUri, tppExplicitAuthorisationPreferred, psuIpAddress, psuIpPort, psuAccept, psuAcceptCharset, psuAcceptEncoding, psuAcceptLanguage, psuUserAgent, psuHttpMethod, psuDeviceId, psuGeoLocation);
        Consents consents = consentMapper.toConsents(body);
        ConsentCreationResponse consent = consentService.createConsent(consents, headers);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponseMapper.toConsentResponse201(consent));
    }

    private Headers buildHeaders(String bankCode, UUID xRequestId, String digest, String signature, byte[] tppSignatureCertificate, String psuId, String psuIdType, String psuCorporateId, String psuCorporateIdType, boolean tppRedirectPreferred, String tppRedirectUri, String tppNokRedirectUri, boolean tppExplicitAuthorisationPreferred, String psuIpAddress, String psuIpPort, String psuAccept, String psuAcceptCharset, String psuAcceptEncoding, String psuAcceptLanguage, String psuUserAgent, String psuHttpMethod, UUID psuDeviceId, String psuGeoLocation) {
        return Headers.builder()
                       .bankCode(bankCode)
                       .xRequestId(xRequestId)
                       .digest(digest)
                       .signature(signature)
                       .tppSignatureCertificate(tppSignatureCertificate)
                       .psuId(psuId)
                       .psuIdType(psuIdType)
                       .psuCorporateId(psuCorporateId)
                       .psuCorporateIdType(psuCorporateIdType)
                       .tppRedirectPreferred(tppRedirectPreferred)
                       .tppRedirectUri(tppRedirectUri)
                       .tppNokRedirectUri(tppNokRedirectUri)
                       .tppExplicitAuthorisationPreferred(tppExplicitAuthorisationPreferred)
                       .psuIpAddress(psuIpAddress)
                       .psuIpPort(psuIpPort)
                       .psuAccept(psuAccept)
                       .psuAcceptCharset(psuAcceptCharset)
                       .psuAcceptEncoding(psuAcceptEncoding)
                       .psuAcceptLanguage(psuAcceptLanguage)
                       .psuUserAgent(psuUserAgent)
                       .psuHttpMethod(psuHttpMethod)
                       .psuDeviceId(psuDeviceId)
                       .psuGeoLocation(psuGeoLocation)
                       .build();
    }

}
