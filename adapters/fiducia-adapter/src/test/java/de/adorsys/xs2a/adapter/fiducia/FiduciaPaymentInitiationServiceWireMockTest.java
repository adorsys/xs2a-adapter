package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PERIODIC_PAYMENTS;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ServiceWireMockTest(FiduciaServiceProvider.class)
class FiduciaPaymentInitiationServiceWireMockTest {
    protected static final String PAYMENTS_PAYMENT_ID = "9667011219090020282PSDDE-BAFIN-911360PA4960JJ";
    protected static final String PAYMENTS_AUTHORISATION_ID = "9592111219090020283PSDDE-BAFIN-911360AU4960JJ";
    protected static final String PERIODIC_PAYMENT_ID = "2750410619090020256PSDDE-BAFIN-911360PA4960JJ";
    protected static final String PERIODIC_AUTHORISATION_ID = "0509410619090020257PSDDE-BAFIN-911360AU4960JJ";

    private final PaymentInitiationService service;

    private static final Ids paymentIds = getPaymentIds();
    private static final Ids authorisationIds = getAuthorisationIds();

    FiduciaPaymentInitiationServiceWireMockTest(PaymentInitiationService service) {
        this.service = service;
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void initiatePayment(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse(format("pis/%s/%s/initiate-payment.json", paymentService, paymentProduct));

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(paymentService,
            paymentProduct,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            resolveRequestBody(requestResponse, paymentService));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(PaymentInitationRequestResponse201.class));
    }

    private Object resolveRequestBody(TestRequestResponse requestResponse, PaymentService paymentService) {
        if (paymentService == PERIODIC_PAYMENTS) {
            return requestResponse.requestBody(PeriodicPaymentInitiationMultipartBody.class);
        }

        return requestResponse.requestBody();
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authenticatePsu(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse(format("pis/%s/%s/authenticate-psu.json", paymentService, paymentProduct));

        Response<StartScaprocessResponse> response = service.startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(UpdatePsuAuthentication.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(StartScaprocessResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void selectScaMethod(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse(format("pis/%s/%s/select-sca-method.json", paymentService, paymentProduct));

        Response<SelectPsuAuthenticationMethodResponse> response = service.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(SelectPsuAuthenticationMethod.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(SelectPsuAuthenticationMethodResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authoriseTransaction(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse(format("pis/%s/%s/authorise-transaction.json", paymentService, paymentProduct));

        Response<ScaStatusResponse> response = service.updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(TransactionAuthorisation.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getPaymentStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse(format("pis/%s/%s/get-payment-status.json", paymentService, paymentProduct));

        Response<String> response = service.getPaymentInitiationStatusAsString(paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody());
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getScaStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse(format("pis/%s/%s/get-sca-status.json", paymentService, paymentProduct));

        Response<ScaStatusResponse> response = service.getPaymentInitiationScaStatus(paymentService,
            paymentProduct,
            paymentIds.get(paymentService, paymentProduct),
            authorisationIds.get(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    private static Stream<Arguments> paymentTypes() {
        return Stream.of(arguments(PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS),
            arguments(PERIODIC_PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS));
    }

    private static Ids getPaymentIds() {
        return new Ids()
            .add(PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENTS_PAYMENT_ID)
            .add(PERIODIC_PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PERIODIC_PAYMENT_ID);
    }

    private static Ids getAuthorisationIds() {
        return new Ids()
            .add(PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENTS_AUTHORISATION_ID)
            .add(PERIODIC_PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PERIODIC_AUTHORISATION_ID);
    }

    private static class Ids {
        private final Map<PaymentService, Map<PaymentProduct, String>> map = new EnumMap<>(PaymentService.class);

        Ids add(PaymentService paymentService, PaymentProduct paymentProduct, String id) {
            map.computeIfAbsent(paymentService, k -> new EnumMap<>(PaymentProduct.class))
                .put(paymentProduct, id);
            return this;
        }

        String get(PaymentService paymentService, PaymentProduct paymentProduct) {
            return Optional.ofNullable(map.get(paymentService))
                .map(m -> m.get(paymentProduct))
                .orElseThrow(NoSuchElementException::new);
        }
    }
}
