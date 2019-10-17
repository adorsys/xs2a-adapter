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
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.TransactionsReport;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.http.protocol.HTTP.DATE_HEADER;

public class FiduciaAccountInformationService extends BaseAccountInformationService {
    private static final Set<String> SUPPORTED_BOOKING_STATUSES = new HashSet<>(Collections.singletonList("booked"));
    private static final String BOOKING_STATUS_ERROR_MESSAGE = String.format(
        "ASPSP supports only the following booking statuses: %s. " +
            "The booking status from the request has to be changed to the supported ones.",
        SUPPORTED_BOOKING_STATUSES
    );

    public FiduciaAccountInformationService(String baseUri,
                                            HttpClient httpClient,
                                            Request.Builder.Interceptor requestBuilderInterceptor) {
        super(baseUri, httpClient, requestBuilderInterceptor);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    public Response<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        if (notSupportedBookingStatus(requestParams)) {
            throw new BadRequestException(BOOKING_STATUS_ERROR_MESSAGE);
        }

        return super.getTransactionList(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        if (notSupportedBookingStatus(requestParams)) {
            throw new BadRequestException(BOOKING_STATUS_ERROR_MESSAGE);
        }

        return super.getTransactionListAsString(accountId, requestHeaders, requestParams);
    }

    private boolean notSupportedBookingStatus(RequestParams requestParams) {
        String bookingStatus = requestParams.toMap().get(RequestParams.BOOKING_STATUS);
        return bookingStatus != null && !SUPPORTED_BOOKING_STATUSES.contains(bookingStatus);
    }
}
