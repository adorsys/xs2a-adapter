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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.gateway.api.AccountApi;
import de.adorsys.xs2a.gateway.api.ConsentApi;
import de.adorsys.xs2a.gateway.mapper.*;
import de.adorsys.xs2a.gateway.model.ais.ConsentInformationResponse200Json;
import de.adorsys.xs2a.gateway.model.ais.ConsentStatusResponse200;
import de.adorsys.xs2a.gateway.model.ais.ConsentsResponse201;
import de.adorsys.xs2a.gateway.model.ais.ConsentsTO;
import de.adorsys.xs2a.gateway.model.shared.StartScaprocessResponseTO;
import de.adorsys.xs2a.gateway.model.shared.UpdatePsuAuthenticationTO;
import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.gateway.service.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ConsentController extends AbstractController implements ConsentApi, AccountApi {

    private final AccountInformationService consentService;

    private final ObjectMapper objectMapper;

    private final ConsentMapper consentMapper = Mappers.getMapper(ConsentMapper.class);

    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);

    private final ConsentInformationMapper consentInformationMapper = Mappers.getMapper(ConsentInformationMapper.class);

    private final ConsentStatusResponseMapper consentStatusResponseMapper = Mappers.getMapper(ConsentStatusResponseMapper.class);

    private final UpdatePsuAuthenticationMapper updatePsuAuthenticationMapper = Mappers.getMapper(UpdatePsuAuthenticationMapper.class);

    private final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);

    private final AccountListHolderMapper accountListHolderMapper = Mappers.getMapper(AccountListHolderMapper.class);

    public ConsentController(AccountInformationService consentService, ObjectMapper objectMapper) {
        this.consentService = consentService;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<ConsentsResponse201> createConsent(String bankCode, UUID xRequestId, ConsentsTO body, String digest, String signature, byte[] tppSignatureCertificate, String psuId, String psuIdType, String psuCorporateId, String psuCorporateIdType, boolean tppRedirectPreferred, String tppRedirectUri, String tppNokRedirectUri, boolean tppExplicitAuthorisationPreferred, String psuIpAddress, String psuIpPort, String psuAccept, String psuAcceptCharset, String psuAcceptEncoding, String psuAcceptLanguage, String psuUserAgent, String psuHttpMethod, UUID psuDeviceId, String psuGeoLocation) {
        Headers headers = buildHeaders(bankCode, xRequestId, digest, signature, tppSignatureCertificate, psuId, psuIdType, psuCorporateId, psuCorporateIdType, tppRedirectPreferred, tppRedirectUri, tppNokRedirectUri, tppExplicitAuthorisationPreferred, psuIpAddress, psuIpPort, psuAccept, psuAcceptCharset, psuAcceptEncoding, psuAcceptLanguage, psuUserAgent, psuHttpMethod, psuDeviceId, psuGeoLocation);
        Consents consents = consentMapper.toConsents(body);
        ConsentCreationResponse consent = consentService.createConsent(consents, headers);
        return ResponseEntity.status(HttpStatus.CREATED).body(creationResponseMapper.toConsentResponse201(consent));
    }

    @Override
    public ResponseEntity<ConsentInformationResponse200Json> getConsentInformation(String bankCode, String consentId, UUID xRequestID,
                                                                                   String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort,
                                                                                   String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage,
                                                                                   String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = buildHeaders(bankCode, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort,
                psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod,
                psUDeviceID, psUGeoLocation);

        ConsentInformation consentInformation = consentService.getConsentInformation(consentId, headers);
        return ResponseEntity.ok(consentInformationMapper.toConsentInformationResponse200Json(consentInformation));
    }

    @Override
    public ResponseEntity<ConsentStatusResponse200> getConsentStatus(String bankCode, String consentId, UUID xRequestID, String digest,
                                                                     String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept,
                                                                     String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent,
                                                                     String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = buildHeaders(bankCode, xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort,
                                       psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod,
                                       psUDeviceID, psUGeoLocation);

        ConsentStatusResponse consentStatusResponse = consentService.getConsentStatus(consentId, headers);
        return ResponseEntity.ok(consentStatusResponseMapper.toConsentStatusResponse200(consentStatusResponse));
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

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startConsentAuthorisation(
            String consentId,
            Map<String, String> headers,
            UpdatePsuAuthenticationTO body) {
        UpdatePsuAuthentication updatePsuAuthentication = updatePsuAuthenticationMapper.toUpdatePsuAuthentication(body);
        StartScaProcessResponse startScaProcessResponse =
                consentService.startConsentAuthorisation(consentId, Headers.fromMap(headers), updatePsuAuthentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(startScaProcessResponseMapper.toStartScaprocessResponseTO(startScaProcessResponse));
    }

    @Override
    public ResponseEntity<Object> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            Map<String, String> headers,
            Object body) {
//        oneOf: #Different Authorisation Bodies
//                - {}
//                - $ref: "#/components/schemas/updatePsuAuthentication"
//                - $ref: "#/components/schemas/selectPsuAuthenticationMethod"
//                - $ref: "#/components/schemas/transactionAuthorisation"
//        oneOf: #Different Authorisation Bodies
//              - $ref: "#/components/schemas/updatePsuIdenticationResponse" #Update PSU Identification
//              - $ref: "#/components/schemas/updatePsuAuthenticationResponse" #Update PSU Authentication
//              - $ref: "#/components/schemas/selectPsuAuthenticationMethodResponse" #Select Authentication Method
//              - $ref: "#/components/schemas/scaStatusResponse" #Transaction Authorisation

        SelectPsuAuthenticationMethod selectPsuAuthenticationMethod =
                objectMapper.convertValue(body, SelectPsuAuthenticationMethod.class);

        SelectPsuAuthenticationMethodResponse response =
                consentService.updateConsentsPsuData(consentId, authorisationId, Headers.fromMap(headers), selectPsuAuthenticationMethod);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(String bankCode, UUID xRequestID, String consentID, Boolean withBalance, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = Headers.builder()
                                  .bankCode(bankCode)
                                  .xRequestId(xRequestID)
                                  .consentId(consentID)
                                  .digest(digest)
                                  .signature(signature)
                                  .tppSignatureCertificate(tpPSignatureCertificate)
                                  .psuIpAddress(psUIPAddress)
                                  .psuIpPort(psUIPPort)
                                  .psuAccept(psUAccept)
                                  .psuAcceptCharset(psUAcceptCharset)
                                  .psuAcceptEncoding(psUAcceptEncoding)
                                  .psuAcceptLanguage(psUAcceptLanguage)
                                  .psuUserAgent(psUUserAgent)
                                  .psuHttpMethod(psUHttpMethod)
                                  .psuDeviceId(psUDeviceID)
                                  .psuGeoLocation(psUGeoLocation)
                                  .build();

        RequestParams requestParams = RequestParams.builder()
                                              .withBalance(withBalance)
                                              .build();

        AccountListHolder accountListHolder = consentService.getAccountList(headers, requestParams);

        return ResponseEntity.status(HttpStatus.OK)
                       .body(accountListHolderMapper.toAccountListTO(accountListHolder));
    }

    // fixes compile error (inheritance diamond problem due to the same method names in ConsentApi and AccountApi interfaces)
    @Override
    public Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    // fixes compile error (inheritance diamond problem due to the same method names in ConsentApi and AccountApi interfaces)
    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    // fixes compile error (inheritance diamond problem due to the same method names in ConsentApi and AccountApi interfaces)
    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }
}
