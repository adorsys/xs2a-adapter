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

package de.adorsys.xs2a.adapter.rest.impl;

import de.adorsys.xs2a.adapter.api.model.*;

import java.util.*;

public class TestModelBuilder {

    private TestModelBuilder() {
    }

    public static final String MESSAGE = "message";
    public static final String CONSTENT_ID = "constent-ID";
    public static final String AUTHORISATION_ID = "authorisation-ID";
    public static final String PAYMENT_ID = "payment-ID";
    public static final String NAME = "SMS OTP on phone +49160 xxxxx 28";
    public static final String TYPE = "SMS_OTP";
    public static final String EXPLANATION = "some explanation";
    public static final String VERSION = "v1.2";
    public static final String METHOD_ID = "authMethodId3";
    public static final String ADDITIONAL_INFO = "additionalInfo";
    public static final List<String> DATA = Collections.singletonList("data");
    public static final String IMAGE = "image";
    public static final String LINK = "http://link-to-image";
    public static final int LENGTH = 123;
    public static final String AUTHENTICATION_METHOD_ID = "authentication-method-ID";
    public static final String SCA_AUTHENTICATION_DATA = "sca-authentication-data";
    public static final String ACCESS_TOKEN = "asdb34nasnd1124fdflsdnasdnw.access.token";
    public static final String REFRESH_TOKEN = "asdasd2141rfgdgjh5656s.refresh.token";
    public static final String SCOPE = "ais:dsadsdasd";
    public static final String TOKEN_TYPE = "type";
    public static final long exripesInSeconds = 3600L;

    public static ConsentsResponse201 buildConsentCreationResponse() {
        ConsentsResponse201 response = new ConsentsResponse201();
        response.setPsuMessage(MESSAGE);
        response.setConsentId(CONSTENT_ID);
        response.setConsentStatus(ConsentStatus.RECEIVED);
        Map<String, HrefType> links = new HashMap<>();
        HrefType link = new HrefType();
        link.setHref(MESSAGE);
        links.put(CONSTENT_ID, link);
        response.setLinks(links);
        response.setScaMethods(Collections.singletonList(buildAuthenticationObject()));
        response.setChosenScaMethod(buildAuthenticationObject());
        response.setChallengeData(buildChallengeData());
        return response;
    }

    public static AuthenticationObject buildAuthenticationObject() {
        AuthenticationObject authenticationObject = new AuthenticationObject();
        authenticationObject.setName(NAME);
        authenticationObject.setAuthenticationType(TYPE);
        authenticationObject.setExplanation(EXPLANATION);
        authenticationObject.setAuthenticationVersion(VERSION);
        authenticationObject.setAuthenticationMethodId(METHOD_ID);
        return authenticationObject;
    }

    public static ChallengeData buildChallengeData() {
        ChallengeData data = new ChallengeData();
        data.setAdditionalInformation(ADDITIONAL_INFO);
        data.setData(DATA);
        data.setImageLink(LINK);
        data.setOtpFormat(ChallengeData.OtpFormat.CHARACTERS);
        data.setOtpMaxLength(LENGTH);
        data.setImage(IMAGE.getBytes());
        return data;
    }

    public static ConsentInformationResponse200Json buildConsentInformationResponse() {
        ConsentInformationResponse200Json consentInformation = new ConsentInformationResponse200Json();
        consentInformation.setConsentStatus(ConsentStatus.RECEIVED);
        consentInformation.setFrequencyPerDay(4);
        consentInformation.setRecurringIndicator(true);
        return consentInformation;
    }

    public static ConsentStatusResponse200 buildConsentStatusResponse() {
        ConsentStatusResponse200 consentStatusResponse = new ConsentStatusResponse200();
        consentStatusResponse.setConsentStatus(ConsentStatus.RECEIVED);
        consentStatusResponse.setPsuMessage(MESSAGE);
        return consentStatusResponse;
    }

    public static Authorisations buildConsentAuthorisationResponse() {
        Authorisations authorisations = new Authorisations();
        authorisations.setAuthorisationIds(Collections.singletonList(AUTHORISATION_ID));
        return authorisations;
    }

    public static StartScaprocessResponse buildStartScaprocessResponse() {
        StartScaprocessResponse startScaprocessResponse = new StartScaprocessResponse();
        startScaprocessResponse.setAuthorisationId(AUTHORISATION_ID);
        startScaprocessResponse.setPsuMessage(MESSAGE);
        startScaprocessResponse.setScaStatus(ScaStatus.STARTED);
        return startScaprocessResponse;
    }

    public static UpdatePsuAuthentication buildUpdatePsuAuthentication() {
        UpdatePsuAuthentication updatePsuAuthentication = new UpdatePsuAuthentication();
        updatePsuAuthentication.setPsuData(new PsuData());
        return updatePsuAuthentication;
    }

    public static UpdatePsuAuthenticationResponse buildUpdatePsuAuthenticationResponse() {
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = new UpdatePsuAuthenticationResponse();
        updatePsuAuthenticationResponse.setPsuMessage(MESSAGE);
        updatePsuAuthenticationResponse.setScaStatus(ScaStatus.STARTED);
        updatePsuAuthenticationResponse.setAuthorisationId(AUTHORISATION_ID);
        return updatePsuAuthenticationResponse;
    }

    public static SelectPsuAuthenticationMethodResponse buildSelectPsuAuthenticationMethodResponse() {
        SelectPsuAuthenticationMethodResponse response = new SelectPsuAuthenticationMethodResponse();
        response.setScaStatus(ScaStatus.SCAMETHODSELECTED);
        response.setPsuMessage(MESSAGE);
        return response;
    }

    public static SelectPsuAuthenticationMethod buildSelectPsuAuthenticationMethod() {
        SelectPsuAuthenticationMethod selectPsuAuthenticationMethod = new SelectPsuAuthenticationMethod();
        selectPsuAuthenticationMethod.setAuthenticationMethodId(AUTHENTICATION_METHOD_ID);
        return selectPsuAuthenticationMethod;
    }

    public static ScaStatusResponse buildScaStatusResponse() {
        ScaStatusResponse scaStatusResponse = new ScaStatusResponse();
        scaStatusResponse.setScaStatus(ScaStatus.FINALISED);
        return scaStatusResponse;
    }

    public static TransactionAuthorisation buildTransactionAuthorisation() {
        TransactionAuthorisation transactionAuthorisation = new TransactionAuthorisation();
        transactionAuthorisation.setScaAuthenticationData(SCA_AUTHENTICATION_DATA);
        return transactionAuthorisation;
    }

    public static AccountList buildAccountList() {
        AccountList accountList = new AccountList();
        accountList.setAccounts(Arrays.asList(new AccountDetails(), new AccountDetails()));
        return accountList;
    }

    public static OK200AccountDetails buildAccountDetails() {
        OK200AccountDetails accountDetails = new OK200AccountDetails();
        accountDetails.setAccount(new AccountDetails());
        return accountDetails;
    }

    public static TransactionsResponse200Json buildTransactionsResponse() {
        TransactionsResponse200Json transactionsResponse200Json = new TransactionsResponse200Json();
        Map<String, HrefType> links = new HashMap<>();
        HrefType link = new HrefType();
        link.setHref(MESSAGE);
        links.put(CONSTENT_ID, link);
        transactionsResponse200Json.setLinks(links);
        return transactionsResponse200Json;
    }

    public static ReadCardAccountBalanceResponse200 buildReadCardAccountBalanceResponse() {
        ReadCardAccountBalanceResponse200 readCardAccountBalanceResponse200
            = new ReadCardAccountBalanceResponse200();
        readCardAccountBalanceResponse200.setBalances(Arrays.asList(new Balance(), new Balance()));
        return readCardAccountBalanceResponse200;
    }

    public static CardAccountsTransactionsResponse200 buildCardAccountsTransactionsResponse() {
        CardAccountsTransactionsResponse200 cardAccountsTransactionsResponse200
            = new CardAccountsTransactionsResponse200();
        Map<String, HrefType> links = new HashMap<>();
        HrefType link = new HrefType();
        link.setHref(MESSAGE);
        links.put(CONSTENT_ID, link);
        cardAccountsTransactionsResponse200.setLinks(links);
        return cardAccountsTransactionsResponse200;
    }

    public static ReadAccountBalanceResponse200 buildReadAccountBalanceResponse() {
        ReadAccountBalanceResponse200 readAccountBalanceResponse200 =
            new ReadAccountBalanceResponse200();
        readAccountBalanceResponse200.setBalances(Arrays.asList(new Balance(), new Balance()));
        return readAccountBalanceResponse200;
    }

    public static TokenResponse buildTokenResponse() {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(ACCESS_TOKEN);
        tokenResponse.setExpiresInSeconds(exripesInSeconds);
        tokenResponse.setRefreshToken(REFRESH_TOKEN);
        tokenResponse.setScope(SCOPE);
        tokenResponse.setTokenType(TOKEN_TYPE);
        return tokenResponse;
    }

    public static PaymentInitationRequestResponse201 buildPaymentInitationRequestResponse() {
        PaymentInitationRequestResponse201 paymentInitationRequestResponse201
            = new PaymentInitationRequestResponse201();
        paymentInitationRequestResponse201.setPaymentId(PAYMENT_ID);
        Map<String, HrefType> links = new HashMap<>();
        HrefType link = new HrefType();
        link.setHref(MESSAGE);
        links.put(PAYMENT_ID, link);
        paymentInitationRequestResponse201.setLinks(links);
        paymentInitationRequestResponse201.setPsuMessage(MESSAGE);
        paymentInitationRequestResponse201.setTransactionFeeIndicator(true);
        return paymentInitationRequestResponse201;
    }

    public static PaymentInitiationStatusResponse200Json buildPaymentInitiationStatusResponse() {
        PaymentInitiationStatusResponse200Json paymentInitiationStatusResponse200Json
            = new PaymentInitiationStatusResponse200Json();
        paymentInitiationStatusResponse200Json.setTransactionStatus(TransactionStatus.ACCC);
        paymentInitiationStatusResponse200Json.setFundsAvailable(true);
        paymentInitiationStatusResponse200Json.setPsuMessage(MESSAGE);
        return paymentInitiationStatusResponse200Json;
    }
}
