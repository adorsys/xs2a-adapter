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

package de.adorsys.xs2a.gateway.service;

import java.util.UUID;

public class ConsentCreationHeaders {
    private String bankCode;
    private UUID xRequestID;
    private String digest;
    private String signature;
    private byte[] tpPSignatureCertificate;
    private String psuId;
    private String psUIDType;
    private String psUCorporateID;
    private String psUCorporateIDType;
    private boolean tpPRedirectPreferred;
    private String tpPRedirectURI;
    private String tpPNokRedirectURI;
    private boolean tpPExplicitAuthorisationPreferred;
    private String psUIPAddress;
    private String psUIPPort;
    private String psUAccept;
    private String psUAcceptCharset;
    private String psUAcceptEncoding;
    private String psUAcceptLanguage;
    private String psUUserAgent;
    private String psUHttpMethod;
    private UUID psUDeviceID;
    private String psUGeoLocation;

    public static ConsentCreationHeadersBuilder builder() {
        return new ConsentCreationHeadersBuilder();
    }

    public static final class ConsentCreationHeadersBuilder {
        private String bankCode;
        private UUID xRequestID;
        private String digest;
        private String signature;
        private byte[] tpPSignatureCertificate;
        private String psuId;
        private String psUIDType;
        private String psUCorporateID;
        private String psUCorporateIDType;
        private boolean tpPRedirectPreferred;
        private String tpPRedirectURI;
        private String tpPNokRedirectURI;
        private boolean tpPExplicitAuthorisationPreferred;
        private String psUIPAddress;
        private String psUIPPort;
        private String psUAccept;
        private String psUAcceptCharset;
        private String psUAcceptEncoding;
        private String psUAcceptLanguage;
        private String psUUserAgent;
        private String psUHttpMethod;
        private UUID psUDeviceID;
        private String psUGeoLocation;

        private ConsentCreationHeadersBuilder() {
        }

        public ConsentCreationHeadersBuilder bankCode(String bankCode) {
            this.bankCode = bankCode;
            return this;
        }

        public ConsentCreationHeadersBuilder xRequestID(UUID xRequestID) {
            this.xRequestID = xRequestID;
            return this;
        }

        public ConsentCreationHeadersBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public ConsentCreationHeadersBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public ConsentCreationHeadersBuilder tppSignatureCertificate(byte[] tpPSignatureCertificate) {
            this.tpPSignatureCertificate = tpPSignatureCertificate;
            return this;
        }

        public ConsentCreationHeadersBuilder psuId(String psuId) {
            this.psuId = psuId;
            return this;
        }

        public ConsentCreationHeadersBuilder psUIDType(String psUIDType) {
            this.psUIDType = psUIDType;
            return this;
        }

        public ConsentCreationHeadersBuilder psUCorporateID(String psUCorporateID) {
            this.psUCorporateID = psUCorporateID;
            return this;
        }

        public ConsentCreationHeadersBuilder psUCorporateIDType(String psUCorporateIDType) {
            this.psUCorporateIDType = psUCorporateIDType;
            return this;
        }

        public ConsentCreationHeadersBuilder tpPRedirectPreferred(boolean tpPRedirectPreferred) {
            this.tpPRedirectPreferred = tpPRedirectPreferred;
            return this;
        }

        public ConsentCreationHeadersBuilder tpPRedirectURI(String tpPRedirectURI) {
            this.tpPRedirectURI = tpPRedirectURI;
            return this;
        }

        public ConsentCreationHeadersBuilder tpPNokRedirectURI(String tpPNokRedirectURI) {
            this.tpPNokRedirectURI = tpPNokRedirectURI;
            return this;
        }

        public ConsentCreationHeadersBuilder tpPExplicitAuthorisationPreferred(boolean tpPExplicitAuthorisationPreferred) {
            this.tpPExplicitAuthorisationPreferred = tpPExplicitAuthorisationPreferred;
            return this;
        }

        public ConsentCreationHeadersBuilder psUIPAddress(String psUIPAddress) {
            this.psUIPAddress = psUIPAddress;
            return this;
        }

        public ConsentCreationHeadersBuilder psUIPPort(String psUIPPort) {
            this.psUIPPort = psUIPPort;
            return this;
        }

        public ConsentCreationHeadersBuilder psUAccept(String psUAccept) {
            this.psUAccept = psUAccept;
            return this;
        }

        public ConsentCreationHeadersBuilder psUAcceptCharset(String psUAcceptCharset) {
            this.psUAcceptCharset = psUAcceptCharset;
            return this;
        }

        public ConsentCreationHeadersBuilder psUAcceptEncoding(String psUAcceptEncoding) {
            this.psUAcceptEncoding = psUAcceptEncoding;
            return this;
        }

        public ConsentCreationHeadersBuilder psUAcceptLanguage(String psUAcceptLanguage) {
            this.psUAcceptLanguage = psUAcceptLanguage;
            return this;
        }

        public ConsentCreationHeadersBuilder psUUserAgent(String psUUserAgent) {
            this.psUUserAgent = psUUserAgent;
            return this;
        }

        public ConsentCreationHeadersBuilder psUHttpMethod(String psUHttpMethod) {
            this.psUHttpMethod = psUHttpMethod;
            return this;
        }

        public ConsentCreationHeadersBuilder psUDeviceID(UUID psUDeviceID) {
            this.psUDeviceID = psUDeviceID;
            return this;
        }

        public ConsentCreationHeadersBuilder psUGeoLocation(String psUGeoLocation) {
            this.psUGeoLocation = psUGeoLocation;
            return this;
        }

        public ConsentCreationHeaders build() {
            ConsentCreationHeaders consentCreationHeaders = new ConsentCreationHeaders();
            consentCreationHeaders.bankCode = this.bankCode;
            consentCreationHeaders.psUIPAddress = this.psUIPAddress;
            consentCreationHeaders.psUAcceptCharset = this.psUAcceptCharset;
            consentCreationHeaders.psUHttpMethod = this.psUHttpMethod;
            consentCreationHeaders.psUIDType = this.psUIDType;
            consentCreationHeaders.psUCorporateID = this.psUCorporateID;
            consentCreationHeaders.xRequestID = this.xRequestID;
            consentCreationHeaders.digest = this.digest;
            consentCreationHeaders.tpPExplicitAuthorisationPreferred = this.tpPExplicitAuthorisationPreferred;
            consentCreationHeaders.psUAcceptLanguage = this.psUAcceptLanguage;
            consentCreationHeaders.tpPNokRedirectURI = this.tpPNokRedirectURI;
            consentCreationHeaders.tpPRedirectPreferred = this.tpPRedirectPreferred;
            consentCreationHeaders.psuId = this.psuId;
            consentCreationHeaders.psUUserAgent = this.psUUserAgent;
            consentCreationHeaders.psUDeviceID = this.psUDeviceID;
            consentCreationHeaders.psUIPPort = this.psUIPPort;
            consentCreationHeaders.psUAcceptEncoding = this.psUAcceptEncoding;
            consentCreationHeaders.psUAccept = this.psUAccept;
            consentCreationHeaders.tpPSignatureCertificate = this.tpPSignatureCertificate;
            consentCreationHeaders.psUGeoLocation = this.psUGeoLocation;
            consentCreationHeaders.signature = this.signature;
            consentCreationHeaders.tpPRedirectURI = this.tpPRedirectURI;
            consentCreationHeaders.psUCorporateIDType = this.psUCorporateIDType;
            return consentCreationHeaders;
        }
    }
}
