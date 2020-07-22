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

package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.api.model.PsuData;
import de.adorsys.xs2a.adapter.api.model.StartScaprocessResponse;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request.Builder.Interceptor;
import de.adorsys.xs2a.adapter.service.PsuPasswordEncryptionService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DeutscheBankAccountInformationService extends BaseAccountInformationService {
    private static final String DATE_HEADER = "Date";

    private final PsuPasswordEncryptionService psuPasswordEncryptionService;

    public DeutscheBankAccountInformationService(Aspsp aspsp,
                                                 HttpClient httpClient,
                                                 Interceptor interceptor,
                                                 LinksRewriter linksRewriter,
                                                 PsuPasswordEncryptionService psuPasswordEncryptionService) {
        super(aspsp, httpClient, interceptor, linksRewriter);
        this.psuPasswordEncryptionService = psuPasswordEncryptionService;
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        PsuData psuData = updatePsuAuthentication.getPsuData();

        if (passwordEncryptionRequired(psuData)) {
            encryptPassword(psuData);
        }

        return super.startConsentAuthorisation(consentId, requestHeaders, requestParams, updatePsuAuthentication);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        PsuData psuData = updatePsuAuthentication.getPsuData();

        if (passwordEncryptionRequired(psuData)) {
            encryptPassword(psuData);
        }

        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication);
    }

    private boolean passwordEncryptionRequired(PsuData psuData) {
        return StringUtils.isNotBlank(psuData.getEncryptedPassword());
    }

    private void encryptPassword(PsuData psuData) {
        String password = psuData.getEncryptedPassword();
        String encryptedPassword = psuPasswordEncryptionService.encrypt(password);
        psuData.setEncryptedPassword(encryptedPassword);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(ACCEPT_HEADER, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        map.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        map.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return map;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }
}
