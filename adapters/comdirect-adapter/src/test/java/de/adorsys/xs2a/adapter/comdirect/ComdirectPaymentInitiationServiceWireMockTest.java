package de.adorsys.xs2a.adapter.comdirect;

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

@ServiceWireMockTest(ComdirectServiceProvider.class)
class ComdirectPaymentInitiationServiceWireMockTest {

    private static final String PAYMENT_ID = "PAYMENT_ID_RCVD_SCT";
    private static final String AUTHORISATION_ID = "11111111-1111-1111-1111-111111111111";

    private final PaymentInitiationService paymentInitiationService;

    ComdirectPaymentInitiationServiceWireMockTest(PaymentInitiationService paymentInitiationService) {
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
