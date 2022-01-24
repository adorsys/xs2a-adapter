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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PaymentInitiationLinksTemplate extends LinksTemplate {

    // link templates:
    private static final String START_AUTHORISATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations";
    private static final String START_AUTHORISATION_WITH_PSU_IDENTIFICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations";
    private static final String UPDATE_PSU_IDENTIFICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations";
    private static final String UPDATE_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String UPDATE_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String SELECT_AUTHENTICATION_METHOD_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String AUTHORISE_TRANSACTION_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";
    private static final String SELF_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}";
    private static final String STATUS_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/status";
    private static final String SCA_STATUS_TEMPLATE = "{host}/{version}/{paymentService}/{paymentProduct}/{paymentId}/authorisations/{authorisationId}";

    private final Map<String, String> linkNameToTemplate;

    public PaymentInitiationLinksTemplate() {
        linkNameToTemplate = new HashMap<>();

        linkNameToTemplate.put(START_AUTHORISATION, START_AUTHORISATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_PSU_IDENTIFICATION,
            START_AUTHORISATION_WITH_PSU_IDENTIFICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_PSU_IDENTIFICATION, UPDATE_PSU_IDENTIFICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_PSU_AUTHENTICATION,
            START_AUTHORISATION_WITH_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_PSU_AUTHENTICATION, UPDATE_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION,
            START_AUTHORISATION_WITH_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(UPDATE_ENCRYPTED_PSU_AUTHENTICATION,
            UPDATE_ENCRYPTED_PSU_AUTHENTICATION_TEMPLATE);
        linkNameToTemplate.put(START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION,
            START_AUTHORISATION_WITH_TRANSACTION_AUTHORISATION_TEMPLATE);
        linkNameToTemplate.put(SELECT_AUTHENTICATION_METHOD, SELECT_AUTHENTICATION_METHOD_TEMPLATE);
        linkNameToTemplate.put(AUTHORISE_TRANSACTION, AUTHORISE_TRANSACTION_TEMPLATE);
        linkNameToTemplate.put(SELF, SELF_TEMPLATE);
        linkNameToTemplate.put(STATUS, STATUS_TEMPLATE);
        linkNameToTemplate.put(SCA_STATUS, SCA_STATUS_TEMPLATE);
    }

    @Override
    public Optional<String> get(String linkName) {
        return Optional.ofNullable(linkNameToTemplate.get(linkName));
    }

    @Override
    public void set(String linkName, String linkTemplate) {
        linkNameToTemplate.put(linkName, linkTemplate);
    }
}
