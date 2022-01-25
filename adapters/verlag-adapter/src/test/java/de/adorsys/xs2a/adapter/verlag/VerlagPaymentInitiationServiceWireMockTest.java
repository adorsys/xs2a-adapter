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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ServiceWireMockTest(TestVerlagServiceProvider.class)
class VerlagPaymentInitiationServiceWireMockTest {
    protected static final String PAYMENTS_SCT_PAYMENT_ID = "fwD0xfgmDk9sGQViY-hS-fzlme_eDCUx3yjmKFt1Qky8lLRAzZIEXwk1k_5JbtVSTXPei8-LpTJAvgJsTrnLs_SdMWF3876hAweK_n7HJlg=_=_psGLvQpt9Q";
    protected static final String PAYMENTS_SCT_AUTHORISATION_ID = "56fb9bd4-1c56-448e-8d45-b65bb42373c8";
    protected static final String PAYMENTS_PAIN_PAYMENT_ID = "tIsb8fiD9_bi1gDuN7EwhAlPkOdfHwgvoAIiksmOVVYOhIu0pokCEKbQldu1TJJb2JZg8bL_p92Ot1RZiwTlEPSdMWF3876hAweK_n7HJlg=_=_psGLvQpt9Q";
    protected static final String PAYMENTS_PAIN_AUTHORISATION_ID = "b890406b-f872-4889-9242-6891f5d6967b";
    protected static final String PERIODIC_SCT_PAYMENT_ID = "Q0RrZV0EJeCvkSBG-l43vgV3yDa4kmPXnXLyXsRdg-YuY6TWANrBE4d49FUDqNwzCiLVnsE-K0LFvitlevm4mPSdMWF3876hAweK_n7HJlg=_=_psGLvQpt9Q";
    protected static final String PERIODIC_SCT_AUTHORISATION_ID = "ce9beeb9-2df6-4ce0-b73a-c6a3a6a597d3";
    private static final Ids ids = initiateMap();
    private final PaymentInitiationService service;

    VerlagPaymentInitiationServiceWireMockTest(PaymentInitiationService service) {
        this.service = service;
    }

    private static Ids initiateMap() {
        return new Ids()
            .add(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENTS_SCT_PAYMENT_ID, PAYMENTS_SCT_AUTHORISATION_ID)
            .add(PaymentService.PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENTS_PAIN_PAYMENT_ID, PAYMENTS_PAIN_AUTHORISATION_ID)
            .add(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PERIODIC_SCT_PAYMENT_ID, PERIODIC_SCT_AUTHORISATION_ID);
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void initiatePayment(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse("pis/" + paymentService + "/" + paymentProduct + "/initiate-payment.json");

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(paymentService,
            paymentProduct,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            getRequestBody(paymentService, paymentProduct, requestResponse));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(PaymentInitationRequestResponse201.class));
    }

    private Object getRequestBody(PaymentService paymentService, PaymentProduct paymentProduct, TestRequestResponse requestResponse) {
        if (paymentService == PaymentService.PAYMENTS) {
            if (paymentProduct == PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS) {
                return requestResponse.requestBody();
            // SEPA_CREDIT_TRANSFERS
            } else {
                return requestResponse.requestBody(PaymentInitiationJson.class);
            }
        } else if (paymentService == PaymentService.PERIODIC_PAYMENTS) {
            if (paymentProduct == PaymentProduct.SEPA_CREDIT_TRANSFERS) {
                return requestResponse.requestBody(PeriodicPaymentInitiationJson.class);
            }
        }

        return null;
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authenticatePsu(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse("pis/" + paymentService + "/" + paymentProduct + "/authenticate-psu.json");

        Response<StartScaprocessResponse> response = service.startPaymentAuthorisation(paymentService,
            paymentProduct,
            ids.getPaymentId(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(UpdatePsuAuthentication.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(StartScaprocessResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void selectScaMethod(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse("pis/" + paymentService + "/" + paymentProduct + "/select-sca-method.json");

        Response<SelectPsuAuthenticationMethodResponse> response = service.updatePaymentPsuData(paymentService,
            paymentProduct,
            ids.getPaymentId(paymentService, paymentProduct),
            ids.getAuthorisationId(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(SelectPsuAuthenticationMethod.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(SelectPsuAuthenticationMethodResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void authoriseTransaction(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse("pis/" + paymentService + "/" + paymentProduct + "/authorise-transaction.json");

        Response<ScaStatusResponse> response = service.updatePaymentPsuData(paymentService,
            paymentProduct,
            ids.getPaymentId(paymentService, paymentProduct),
            ids.getAuthorisationId(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(TransactionAuthorisation.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @ParameterizedTest
    @MethodSource("paymentTypes")
    void getPaymentStatus(PaymentService paymentService, PaymentProduct paymentProduct) throws Exception {
        TestRequestResponse requestResponse =
            new TestRequestResponse("pis/" + paymentService + "/" + paymentProduct + "/get-payment-status.json");

        Response<PaymentInitiationStatusResponse200Json> response = service.getPaymentInitiationStatus(paymentService,
            paymentProduct,
            ids.getPaymentId(paymentService, paymentProduct),
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(PaymentInitiationStatusResponse200Json.class));
    }

    private static Stream<Arguments> paymentTypes() {
        return Stream.of(arguments(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS),
            arguments(PaymentService.PAYMENTS, PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS),
            arguments(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS));
    }

    private static class Ids {
        private final Map<Pair<PaymentService, PaymentProduct>, Pair<String, String>> ids = new HashMap<>();

        Ids add(PaymentService paymentService, PaymentProduct paymentProduct, String paymentId, String authorisationId) {
            ids.put(Pair.of(paymentService, paymentProduct), Pair.of(paymentId, authorisationId));
            return this;
        }

        String getPaymentId(PaymentService paymentService, PaymentProduct paymentProduct) {
            return ids.get(Pair.of(paymentService, paymentProduct)).getLeft();
        }

        String getAuthorisationId(PaymentService paymentService, PaymentProduct paymentProduct) {
            return ids.get(Pair.of(paymentService, paymentProduct)).getRight();
        }
    }
}
