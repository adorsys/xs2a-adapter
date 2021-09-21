package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PERIODIC_PAYMENTS;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ServiceWireMockTest(UnicreditServiceProvider.class)
class UnicreditPaymentInitiationServiceWireMockTest {

    private static final String PAYMENTS_PAYMENT_ID = "PDEA630971";
    private static final String PAYMENTS_AUTHORISATION_ID = "PDEA630971";
    private static final String PERIODIC_PAYMENT_ID = "PDEA744022";
    private static final String PERIODIC_AUTHORISATION_ID = "PDEA744022";
    private final Map<PaymentService, Pair<String, String>> ids;

    private final PaymentInitiationService paymentInitiationService;

    UnicreditPaymentInitiationServiceWireMockTest(PaymentInitiationService paymentInitiationService) {
        this.paymentInitiationService = paymentInitiationService;
        this.ids = initiateMap();
    }

    private Map<PaymentService, Pair<String, String>> initiateMap() {
        Map<PaymentService, Pair<String, String>> map = new HashMap<>();
        map.put(PaymentService.PAYMENTS, Pair.of(PAYMENTS_PAYMENT_ID, PAYMENTS_AUTHORISATION_ID));
        map.put(PaymentService.PERIODIC_PAYMENTS, Pair.of(PERIODIC_PAYMENT_ID, PERIODIC_AUTHORISATION_ID));
        return map;
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void initiatePayment(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        var requestResponse = new TestRequestResponse(format("pis/%s/%s/initiate-payment.json", paymentService, paymentProduct));

        var response = paymentInitiationService.initiatePayment(paymentService,
            paymentProduct,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(getPaymentClass(paymentService)));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(PaymentInitationRequestResponse201.class));
    }

    private Class<?> getPaymentClass(PaymentService paymentService) {
        if (PAYMENTS == paymentService) {
            return PaymentInitiationJson.class;
        }
        return PeriodicPaymentInitiationJson.class;
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getPaymentStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        var requestResponse = new TestRequestResponse(format("pis/%s/%s/get-payment-status.json", paymentService, paymentProduct));

        var response = paymentInitiationService.getPaymentInitiationStatus(paymentService,
            paymentProduct,
            ids.get(paymentService).getLeft(),
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(PaymentInitiationStatusResponse200Json.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getScaStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        var requestResponse = new TestRequestResponse(format("pis/%s/%s/get-sca-status.json", paymentService, paymentProduct));

        if (PAYMENTS == paymentService) {
            var response = paymentInitiationService.getPaymentInitiationScaStatus(paymentService,
                paymentProduct,
                ids.get(paymentService).getLeft(),
                ids.get(paymentService).getRight(),
                requestResponse.requestHeaders(),
                RequestParams.empty());

            assertThat(response.getBody())
                .isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
        } else { // get SCA Status is not implemented for periodic-payments
            var requestHeaders = requestResponse.requestHeaders();
            var params = RequestParams.empty();

            assertThatThrownBy(() -> paymentInitiationService.getPaymentInitiationScaStatus(paymentService,
                paymentProduct,
                ids.get(paymentService).getLeft(),
                ids.get(paymentService).getRight(),
                requestHeaders,
                params))
                .asInstanceOf(type(ErrorResponseException.class))
                .matches(er -> 404 == er.getStatusCode())
                .extracting(ErrorResponseException::getErrorResponse, optional(ErrorResponse.class))
                .contains(requestResponse.responseBody(ErrorResponse.class));
        }
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authorizeTransaction(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        var requestResponse = new TestRequestResponse(format("pis/%s/%s/authorise-transaction.json", paymentService, paymentProduct));

        var response = paymentInitiationService.updatePaymentPsuData(paymentService,
            paymentProduct,
            ids.get(paymentService).getLeft(),
            ids.get(paymentService).getRight(),
            requestResponse.requestHeaders(),
            requestResponse.requestParams(),
            requestResponse.requestBody(TransactionAuthorisation.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authenticatePsu(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        var requestResponse = new TestRequestResponse(format("pis/%s/%s/authenticate-psu.json", paymentService, paymentProduct));

        var response = paymentInitiationService.startPaymentAuthorisation(paymentService,
            paymentProduct,
            ids.get(paymentService).getLeft(),
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(StartScaprocessResponse.class));
    }

    @Disabled("Sandbox and Production environments have different UpdatePsuAuthentication models, thus can't run this test for" +
        "avoiding MismatchedInputException")
    @ParameterizedTest
    @MethodSource("paymentTypes")
    void updatePsuAuthentication(PaymentService paymentService, PaymentProduct paymentProduct) throws IOException {
        var requestResponse = new TestRequestResponse(format("pis/%s/%s/update-psu-authentication.json", paymentService, paymentProduct));

        var response = paymentInitiationService.updatePaymentPsuData(paymentService,
            paymentProduct,
            ids.get(paymentService).getLeft(),
            ids.get(paymentService).getRight(),
            requestResponse.requestHeaders(),
            requestResponse.requestParams(),
            requestResponse.requestBody(UpdatePsuAuthentication.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(UpdatePsuAuthenticationResponse.class));
    }

    private static Stream<Arguments> paymentTypes() {
        return Stream.of(arguments(PAYMENTS, SEPA_CREDIT_TRANSFERS),
            arguments(PERIODIC_PAYMENTS, SEPA_CREDIT_TRANSFERS));
    }
}
