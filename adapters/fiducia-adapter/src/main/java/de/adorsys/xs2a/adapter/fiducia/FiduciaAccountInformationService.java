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

package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.impl.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.fiducia.mapper.FiduciaMapper;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaOK200TransactionDetails;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaStartScaProcessResponse;
import de.adorsys.xs2a.adapter.fiducia.model.FiduciaTransactionsResponse200Json;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.http.protocol.HTTP.DATE_HEADER;

public class FiduciaAccountInformationService extends BaseAccountInformationService {
    private static final Set<String> SUPPORTED_BOOKING_STATUSES = new HashSet<>(Collections.singletonList("booked"));
    private static final String BOOKING_STATUS_ERROR_MESSAGE = String.format(
        "ASPSP supports only the following booking statuses: %s. " +
            "The booking status from the request has to be changed to the supported ones.",
        SUPPORTED_BOOKING_STATUSES
    );
    private static final FiduciaMapper mapper = Mappers.getMapper(FiduciaMapper.class);

    public FiduciaAccountInformationService(Aspsp aspsp,
                                            HttpClient httpClient,
                                            Request.Builder.Interceptor requestBuilderInterceptor,
                                            LinksRewriter linksRewriter) {
        super(aspsp, httpClient, requestBuilderInterceptor, linksRewriter);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return withDateHeader(headers);
    }

    private Map<String, String> withDateHeader(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return withDateHeader(headers);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return withDateHeader(headers);
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return withDateHeader(headers);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        modifyRecurringIndicator(body);
        return super.createConsent(requestHeaders, requestParams, body);
    }

    // Needed to fix the issue on Fiducia side: ASPSP doesn't accept recurring indicator being equal to false.
    private void modifyRecurringIndicator(Consents body) {
        body.setRecurringIndicator(true);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return getTransactionList(accountId,
            requestHeaders,
            requestParams,
            FiduciaTransactionsResponse200Json.class,
            mapper::toTransactionsResponse200Json);
    }

    @Override
    public List<ValidationError> validateGetTransactionList(String accountId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams) {
        return validateBookingStatus(requestParams);
    }

    private List<ValidationError> validateBookingStatus(RequestParams requestParams) {
        if (notSupportedBookingStatus(requestParams)) {
            return Collections.singletonList(new ValidationError(ValidationError.Code.NOT_SUPPORTED,
                RequestParams.BOOKING_STATUS,
                BOOKING_STATUS_ERROR_MESSAGE));
        }
        return Collections.emptyList();
    }

    private boolean notSupportedBookingStatus(RequestParams requestParams) {
        String bookingStatus = requestParams.toMap().get(RequestParams.BOOKING_STATUS);
        return bookingStatus != null && !SUPPORTED_BOOKING_STATUSES.contains(bookingStatus);
    }

    @Override
    public List<ValidationError> validateGetTransactionListAsString(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return validateBookingStatus(requestParams);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return getTransactionDetails(accountId,
            transactionId,
            requestHeaders,
            requestParams,
            FiduciaOK200TransactionDetails.class,
            mapper::toOK200TransactionDetails);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(
        String consentId,
        String authorisationId,
        RequestHeaders requestHeaders,
        RequestParams requestParams,
        SelectPsuAuthenticationMethod selectPsuAuthenticationMethod
    ) {
        return super.updateConsentsPsuData(consentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod,
            FiduciaSelectPsuAuthenticationMethodResponse.class,
            mapper::toSelectPsuAuthenticationMethodResponse);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(
        String consentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams,
        UpdatePsuAuthentication updatePsuAuthentication
    ) {
        return super.startConsentAuthorisation(consentId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            FiduciaStartScaProcessResponse.class,
            mapper::toStartScaProcessResponse);
    }
}
