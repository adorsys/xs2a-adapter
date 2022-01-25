/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.PsuPasswordEncryptionService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.*;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankOK200TransactionDetails;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DeutscheBankAccountInformationService extends BaseAccountInformationService {
    private static final String DATE_HEADER = "Date";

    private final DeutscheBankMapper deutscheBankMapper = Mappers.getMapper(DeutscheBankMapper.class);
    private final PsuPasswordEncryptionService psuPasswordEncryptionService;

    public DeutscheBankAccountInformationService(Aspsp aspsp,
                                                 HttpClientFactory httpClientFactory,
                                                 List<Interceptor> interceptors,
                                                 LinksRewriter linksRewriter,
                                                 PsuPasswordEncryptionService psuPasswordEncryptionService) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            interceptors,
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
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

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {

        return super.getTransactionList(accountId,
            requestHeaders,
            requestParams,
            DeutscheBankTransactionResponse200Json.class,
            deutscheBankMapper::toTransactionsResponse200Json);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {

        return super.getTransactionDetails(accountId,
            transactionId,
            requestHeaders,
            requestParams,
            DeutscheBankOK200TransactionDetails.class,
            deutscheBankMapper::toOK200TransactionDetails);
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
