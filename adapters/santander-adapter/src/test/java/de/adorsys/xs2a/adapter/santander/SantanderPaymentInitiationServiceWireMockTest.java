package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(SantanderServiceProvider.class)
class SantanderPaymentInitiationServiceWireMockTest {

    private static final String PAYMENT_ID = "73c52dc3-bd44-42b3-bc86-6b61c3167b63";

    private final PaymentInitiationService paymentInitiationService;

    SantanderPaymentInitiationServiceWireMockTest(PaymentInitiationService paymentInitiationService) {
        this.paymentInitiationService = paymentInitiationService;
    }

    @Test
    void initiatePayment() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("pis/payments/sepa-credit-transfers/initiate-payment.json");

        Response<PaymentInitationRequestResponse201> response = paymentInitiationService.initiatePayment(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(PaymentInitiationJson.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(PaymentInitationRequestResponse201.class));
    }

    @Test
    void getPaymentStatus() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("pis/payments/sepa-credit-transfers/get-payment-status.json");

        Response<PaymentInitiationStatusResponse200Json> response = paymentInitiationService.getPaymentInitiationStatus(PaymentService.PAYMENTS,
            PaymentProduct.SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(PaymentInitiationStatusResponse200Json.class));
    }
}
