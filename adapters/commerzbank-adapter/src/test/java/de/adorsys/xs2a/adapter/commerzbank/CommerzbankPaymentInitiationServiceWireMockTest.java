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

package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(CommerzbankServiceProvider.class)
class CommerzbankPaymentInitiationServiceWireMockTest {

    private static final String PAYMENT_ID = "PAYMENT_ID_RCVD_SCT";
    private static final String AUTHORISATION_ID = "22222222-2222-2222-2222-222222222222";

    private final PaymentInitiationService paymentInitiationService;

    CommerzbankPaymentInitiationServiceWireMockTest(PaymentInitiationService paymentInitiationService) {
        this.paymentInitiationService = paymentInitiationService;
    }

    @Test
    void initiatePayment() throws IOException {
        var requestResponse = new TestRequestResponse("pis/payments/sepa-credit-transfers/initiate-payment.json");

        var response = paymentInitiationService.initiatePayment(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(PaymentInitiationJson.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(PaymentInitationRequestResponse201.class));
    }

    @Test
    void getPaymentStatus() throws IOException {
        var requestResponse = new TestRequestResponse("pis/payments/sepa-credit-transfers/get-payment-status.json");

        var response = paymentInitiationService.getPaymentInitiationStatus(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(PaymentInitiationStatusResponse200Json.class));
    }

    @Test
    void getPaymentAuthorisations() throws IOException {
        var requestResponse = new TestRequestResponse("pis/payments/sepa-credit-transfers/get-payment-authorisations.json");

        var response = paymentInitiationService.getPaymentInitiationAuthorisation(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(Authorisations.class));
    }

    @Test
    void getScaStatus() throws IOException {
        var requestResponse = new TestRequestResponse("pis/payments/sepa-credit-transfers/get-sca-status.json");

        var response = paymentInitiationService.getPaymentInitiationScaStatus(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }
}
