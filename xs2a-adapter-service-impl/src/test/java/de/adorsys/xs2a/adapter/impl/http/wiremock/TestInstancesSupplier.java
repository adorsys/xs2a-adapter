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

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.model.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

public class TestInstancesSupplier {

    private static final String IBAN = "IBAN";
    private static final String CURRENCY = "EUR";
    private static final HrefType HREF_TYPE = getHrefType();

    public static Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId("adorsys-adapter");
        return aspsp;
    }

    public static AccountList getAccountList() {
        AccountList accountList = new AccountList();
        accountList.setAccounts(Collections.singletonList(getAccountDetails()));
        return accountList;
    }

    private static AccountDetails getAccountDetails() {
        AccountDetails details = new AccountDetails();
        details.setResourceId("some_value");
        details.setIban(IBAN);
        details.setCurrency(CURRENCY);
        details.setName("user");
        details.setDisplayName("display user");
        details.setProduct("product");
        details.setCashAccountType("CASH");
        details.setStatus(AccountStatus.ENABLED);
        details.setLinkedAccounts("linked_account");
        details.setUsage(AccountDetails.Usage.PRIV);
        details.setLinks(getLinks("balances", "transactions"));
        return details;
    }

    private static Map<String, HrefType> getLinks(String... args) {
        if (args.length == 0) {
            return new HashMap<>();
        }

        Map<String, HrefType> links = new HashMap<>();
        for (String arg : args) {
            links.put(arg, HREF_TYPE);
        }
        return links;
    }

    private static HrefType getHrefType() {
        HrefType hrefType = new HrefType();
        hrefType.setHref("http://foo.boo");
        return hrefType;
    }

    public static Consents getConsents() {
        Consents requestBody = new Consents();
        requestBody.setAccess(getAccess());
        requestBody.setCombinedServiceIndicator(false);
        requestBody.setRecurringIndicator(true);
        requestBody.setValidUntil(LocalDate.now());
        requestBody.setFrequencyPerDay(4);
        return requestBody;
    }

    private static AccountAccess getAccess() {
        AccountAccess accountAccess = new AccountAccess();
        accountAccess.setAccounts(singletonList(getAccountReference()));
        accountAccess.setBalances(singletonList(getAccountReference()));
        accountAccess.setTransactions(singletonList(getAccountReference()));
        return accountAccess;
    }

    private static AccountReference getAccountReference() {
        AccountReference accountReference = new AccountReference();
        accountReference.setIban(IBAN);
        accountReference.setCurrency(CURRENCY);
        return accountReference;
    }

    public static ConsentsResponse201 getConsentResponse201() {
        ConsentsResponse201 consentsResponse = new ConsentsResponse201();
        consentsResponse.setConsentStatus(ConsentStatus.RECEIVED);
        consentsResponse.setConsentId("consent-id");
        consentsResponse.setLinks(getLinks("updatePsuAuthentication", "self", "status", "scaStatus"));
        return consentsResponse;
    }

    public static PaymentInitationRequestResponse201 getPaymentInitiationRequestResponse201() {
        PaymentInitationRequestResponse201 response = new PaymentInitationRequestResponse201();
        response.setTransactionStatus(TransactionStatus.RCVD);
        response.setPaymentId("foo");
        response.setLinks(getLinks("updatePsuAuthentication", "self", "status", "scaStatus"));
        return response;
    }

    public static ResponseHeaders getResponseHeaders(String key, String value) {
        Map<String, String> headers = new HashMap<>();
        headers.put(key, value);
        return ResponseHeaders.fromMap(headers);
    }

    public static <T> Response<T> getResponse(T body) {
        return getResponse(body, ResponseHeaders.emptyResponseHeaders());
    }

    public static <T> Response<T> getResponse(T body, ResponseHeaders responseHeaders) {
        return new Response<>(200, body, responseHeaders);
    }

    public static PeriodicPaymentInitiationXmlPart2StandingorderTypeJson getPeriodicPaymentInitiationXmlPart2StandingorderTypeJson() {
        PeriodicPaymentInitiationXmlPart2StandingorderTypeJson response
            = new PeriodicPaymentInitiationXmlPart2StandingorderTypeJson();
        response.setStartDate(LocalDate.now());
        response.setEndDate(LocalDate.now());
        response.setFrequency(FrequencyCode.WEEKLY);
        response.setDayOfExecution(DayOfExecution._1);
        return response;
    }
}
