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

package de.adorsys.xs2a.adapter.rest.impl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.api.PaymentApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Map;

@RestController
public class PaymentController extends AbstractController implements PaymentApi {
    private static final EnumSet<PaymentService> SUPPORTED_PAYMENT_SERVICES
        = EnumSet.of(PaymentService.PAYMENTS, PaymentService.PERIODIC_PAYMENTS);

    private final PaymentInitiationService paymentService;
    private final HeadersMapper headersMapper;

    public PaymentController(PaymentInitiationService paymentService,
                             HeadersMapper headersMapper,
                             ObjectMapper objectMapper) {
        super(objectMapper);
        this.paymentService = paymentService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(
        PaymentService paymentService,
        PaymentProduct paymentProduct,
        Map<String, String> parameters,
        Map<String, String> headers,
        PeriodicPaymentInitiationMultipartBody body) {

        // multipart request parts passed in the @RequestParam annotated parameter
        // create problems downstream when sent in the outgoing request as query parameters
        for (Field field : body.getClass().getDeclaredFields()) {
            parameters.remove(field.getName());
        }
        return initiatePaymentInternal(paymentService, paymentProduct, parameters, headers, body);
    }

    @Override
    public ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                              PaymentProduct paymentProduct,
                                                                              Map<String, String> parameters,
                                                                              Map<String, String> headers,
                                                                              ObjectNode body) {
        return initiatePaymentInternal(paymentService, paymentProduct, parameters, headers, body);
    }

    private ResponseEntity<PaymentInitationRequestResponse201> initiatePaymentInternal(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       Map<String, String> parameters,
                                                                                       Map<String, String> headers,
                                                                                       Object body) {
        requireSupportedPaymentService(paymentService);
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<PaymentInitationRequestResponse201> response =
            this.paymentService.initiatePayment(paymentService,
                paymentProduct,
                requestHeaders,
                requestParams,
                body);

        return ResponseEntity.status(HttpStatus.CREATED)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    private void requireSupportedPaymentService(PaymentService paymentService) {
        if (!SUPPORTED_PAYMENT_SERVICES.contains(paymentService)) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                              PaymentProduct paymentProduct,
                                                                              Map<String, String> parameters,
                                                                              Map<String, String> headers,
                                                                              String body) {
        return initiatePaymentInternal(paymentService, paymentProduct, parameters, headers, body);
    }

    @Override
    public ResponseEntity<Object> getPaymentInformation(PaymentService paymentService,
                                                        PaymentProduct paymentProduct,
                                                        String paymentId,
                                                        Map<String, String> parameters,
                                                        Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<String> response =
            this.paymentService.getPaymentInformationAsString(paymentService,
                paymentProduct,
                paymentId,
                requestHeaders,
                requestParams);

        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                           PaymentProduct paymentProduct,
                                                                           String paymentId,
                                                                           String authorisationId,
                                                                           Map<String, String> parameters,
                                                                           Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<ScaStatusResponse> response =
            this.paymentService.getPaymentInitiationScaStatus(paymentService,
                paymentProduct,
                paymentId,
                authorisationId,
                requestHeaders,
                requestParams);

        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<Object> getPaymentInitiationStatus(PaymentService paymentService,
                                                             PaymentProduct paymentProduct,
                                                             String paymentId,
                                                             Map<String, String> parameters,
                                                             Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        if (requestHeaders.isAcceptJson()) {
            Response<PaymentInitiationStatusResponse200Json> response =
                this.paymentService.getPaymentInitiationStatus(paymentService,
                    paymentProduct,
                    paymentId,
                    requestHeaders,
                    requestParams);

            return ResponseEntity.status(HttpStatus.OK)
                .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                .body(response.getBody());
        }

        Response<String> response =
            this.paymentService.getPaymentInitiationStatusAsString(paymentService,
                paymentProduct,
                paymentId,
                requestHeaders,
                requestParams);

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                            PaymentProduct paymentProduct,
                                                                            String paymentId,
                                                                            Map<String, String> parameters,
                                                                            Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<Authorisations> response =
            this.paymentService.getPaymentInitiationAuthorisation(paymentService,
                paymentProduct,
                paymentId,
                requestHeaders,
                requestParams);

        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                             PaymentProduct paymentProduct,
                                                                             String paymentId,
                                                                             Map<String, String> parameters,
                                                                             Map<String, String> headers,
                                                                             ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<StartScaprocessResponse> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication ->
                    this.paymentService.startPaymentAuthorisation(paymentService,
                        paymentProduct,
                        paymentId,
                        requestHeaders,
                        requestParams,
                        updatePsuAuthentication),
            (StartAuthorisationHandler) emptyAuthorisationBody ->
                this.paymentService.startPaymentAuthorisation(paymentService,
                    paymentProduct,
                    paymentId,
                    requestHeaders,
                    requestParams)
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<Object> updatePaymentPsuData(PaymentService paymentService,
                                                       PaymentProduct paymentProduct,
                                                       String paymentId,
                                                       String authorisationId,
                                                       Map<String, String> parameters,
                                                       Map<String, String> headers,
                                                       ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<?> response = handleAuthorisationBody(body,
            (UpdatePsuAuthenticationHandler) updatePsuAuthentication ->
                this.paymentService.updatePaymentPsuData(paymentService,
                    paymentProduct,
                    paymentId,
                    authorisationId,
                    requestHeaders,
                    requestParams,
                    updatePsuAuthentication),
            (SelectPsuAuthenticationMethodHandler) selectPsuAuthenticationMethod ->
                this.paymentService.updatePaymentPsuData(paymentService,
                    paymentProduct,
                    paymentId,
                    authorisationId,
                    requestHeaders,
                    requestParams,
                    selectPsuAuthenticationMethod),
            (TransactionAuthorisationHandler) transactionAuthorisation ->
                this.paymentService.updatePaymentPsuData(paymentService,
                    paymentProduct,
                    paymentId,
                    authorisationId,
                    requestHeaders,
                    requestParams,
                    transactionAuthorisation)
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }
}
