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

package de.adorsys.xs2a.adapter.impl.link.bg.template;

import java.util.Optional;

public abstract class LinksTemplate {
    // BG placeholders:
    public static final String HOST_PLACEHOLDER = "{host}";
    public static final String VERSION_PLACEHOLDER = "{version}";
    public static final String CONSENT_ID_PLACEHOLDER = "{consentId}";
    public static final String AUTHORISATION_ID_PLACEHOLDER = "{authorisationId}";
    public static final String ACCOUNT_ID_PLACEHOLDER = "{accountId}";
    public static final String TRANSACTION_ID_PLACEHOLDER = "{transactionId}";
    public static final String PAYMENT_SERVICE_PLACEHOLDER = "{paymentService}";
    public static final String PAYMENT_PRODUCT_PLACEHOLDER = "{paymentProduct}";
    public static final String PAYMENT_ID_PLACEHOLDER = "{paymentId}";

    // BG link names:
    public static final String SCA_REDIRECT = "scaRedirect";
    public static final String SCA_OAUTH = "scaOAuth";
    protected static final String START_AUTHORISATION = "startAuthorisation";
    protected static final String START_AUTHORISATION_WITH_PSU_IDENTIFICATION = "startAuthorisationWithPsuIdentification";
    protected static final String UPDATE_PSU_IDENTIFICATION = "updatePsuIdentification";
    protected static final String START_AUTHORISATION_WITH_PROPRIETARY_DATA = "startAuthorisationWithProprietaryData";
    protected static final String UPDATE_PROPRIETARY_DATA = "updateProprietaryData";
    protected static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION = "startAuthorisationWithPsuAuthentication";
    protected static final String UPDATE_PSU_AUTHENTICATION = "updatePsuAuthentication";
    protected static final String START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION = "startAuthorisationWithEncryptedPsuAuthentication";
    protected static final String UPDATE_ENCRYPTED_PSU_AUTHENTICATION = "updateEncryptedPsuAuthentication";
    protected static final String START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION = "startAuthorisationWithTransactionAuthorisation";
    protected static final String SELECT_AUTHENTICATION_METHOD = "selectAuthenticationMethod";
    protected static final String AUTHORISE_TRANSACTION = "authoriseTransaction";
    protected static final String SELF = "self";
    protected static final String STATUS = "status";
    protected static final String SCA_STATUS = "scaStatus";
    protected static final String ACCOUNT = "account";
    protected static final String BALANCES = "balances";
    protected static final String TRANSACTIONS = "transactions";
    protected static final String TRANSACTION_DETAILS = "transactionDetails";
    protected static final String FIRST = "first";
    protected static final String NEXT = "next";
    protected static final String PREVIOUS = "previous";
    protected static final String LAST = "last";
    protected static final String DOWNLOAD = "download";

    public abstract Optional<String> get(String linkName);

    public abstract void set(String linkName, String linkTemplate);
}
