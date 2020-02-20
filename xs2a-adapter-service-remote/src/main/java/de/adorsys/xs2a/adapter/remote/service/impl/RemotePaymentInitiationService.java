/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.remote.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.mapper.*;
import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.remote.api.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.service.mapper.ResponseHeadersMapper;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class RemotePaymentInitiationService implements PaymentInitiationService {

    private static final Logger log = LoggerFactory.getLogger(RemotePaymentInitiationService.class);

    private final PaymentInitiationClient client;

    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper = new PaymentInitiationScaStatusResponseMapper();
    private final PaymentInitiationRequestResponseMapper initiationRequestResponseMapper = Mappers.getMapper(PaymentInitiationRequestResponseMapper.class);
    private final SinglePaymentInformationMapper singlePaymentInformationMapper = Mappers.getMapper(SinglePaymentInformationMapper.class);
    private final PaymentInitiationStatusMapper paymentInitiationStatusMapper = Mappers.getMapper(PaymentInitiationStatusMapper.class);
    private final PaymentInitiationAuthorisationResponseMapper authorisationResponseMapper = Mappers.getMapper(PaymentInitiationAuthorisationResponseMapper.class);
    private final StartScaProcessResponseMapper scaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ResponseHeadersMapper responseHeadersMapper = Mappers.getMapper(ResponseHeadersMapper.class);
    private final ObjectMapper objectMapper;

    public RemotePaymentInitiationService(PaymentInitiationClient paymentInitiationClient) {
        this(paymentInitiationClient, new ObjectMapper());
    }

    public RemotePaymentInitiationService(PaymentInitiationClient paymentInitiationClient, ObjectMapper objectMapper) {
        this.client = paymentInitiationClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Object o) {

        ResponseEntity<PaymentInitationRequestResponse201TO> responseEntity;
        if (o instanceof String) {
            responseEntity = client.initiatePayment(
                PaymentServiceTO.PAYMENTS,
                PaymentProductTO.fromValue(paymentProduct),
                requestParams.toMap(),
                requestHeaders.toMap(),
                (String) o
            );
        } else {
            responseEntity = client.initiatePayment(
                PaymentServiceTO.PAYMENTS,
                PaymentProductTO.fromValue(paymentProduct),
                requestParams.toMap(),
                requestHeaders.toMap(),
                objectMapper.valueToTree(o)
            );
        }
        PaymentInitiationRequestResponse paymentInitiationRequestResponse =
            initiationRequestResponseMapper.toPaymentInitiationRequestResponse(responseEntity.getBody());
        return new Response<>(responseEntity.getStatusCodeValue(),
            paymentInitiationRequestResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                      String paymentId,
                                                                                                      RequestHeaders requestHeaders,
                                                                                                      RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentServiceTO.PAYMENTS,
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap()
        );
        PaymentInitiationWithStatusResponseTO responseTO = objectMapper.convertValue(responseEntity.getBody(), PaymentInitiationWithStatusResponseTO.class);
        SinglePaymentInitiationInformationWithStatusResponse paymentInitiationSctWithStatusResponse = singlePaymentInformationMapper.toSinglePaymentInitiationInformationWithStatusResponse(responseTO);
        return new Response<>(responseEntity.getStatusCodeValue(), paymentInitiationSctWithStatusResponse, responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                      String spaymentProduct,
                                                                                      String paymentId,
                                                                                      String authorisationId,
                                                                                      RequestHeaders requestHeaders,
                                                                                      RequestParams requestParams) {
        ResponseEntity<ScaStatusResponseTO> responseEntity = client.getPaymentInitiationScaStatus(
            PaymentServiceTO.fromValue(paymentService),
            PaymentProductTO.fromValue(spaymentProduct),
            paymentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap());
        PaymentInitiationScaStatusResponse initiationScaStatusResponse = paymentInitiationScaStatusResponseMapper.toPaymentInitiationScaStatusResponse(responseEntity.getBody());
        return new Response<>(responseEntity.getStatusCodeValue(), initiationScaStatusResponse, responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(PaymentServiceTO.PAYMENTS,
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap());
        PaymentInitiationStatusResponse200JsonTO responseTO = objectMapper.convertValue(responseEntity.getBody(), PaymentInitiationStatusResponse200JsonTO.class);
        PaymentInitiationStatus initiationStatus = paymentInitiationStatusMapper.toPaymentInitiationStatus(responseTO);
        return new Response<>(responseEntity.getStatusCodeValue(), initiationStatus, responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(PaymentServiceTO.PAYMENTS,
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            Collections.emptyMap(), // fixme
            requestHeaders.toMap());
        try {
            String responseObject = objectMapper.writeValueAsString(responseEntity.getBody());
            return new Response<>(responseEntity.getStatusCodeValue(), responseObject, responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
        } catch (JsonProcessingException e) {
            log.error("Failed to convert the response body into a string", e);
        }
        return new Response<>(responseEntity.getStatusCodeValue(), "{}", responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        ResponseEntity<AuthorisationsTO> responseEntity = client.getPaymentInitiationAuthorisation(
            PaymentServiceTO.fromValue(paymentService),
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            Collections.emptyMap(), // fixme
            requestHeaders.toMap());
        PaymentInitiationAuthorisationResponse authorisationResponse = authorisationResponseMapper.toPaymentInitiationAuthorisationResponse(responseEntity.getBody());
        return new Response<>(responseEntity.getStatusCodeValue(), authorisationResponse, responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct, String paymentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startPaymentAuthorisation(
            PaymentServiceTO.PAYMENTS,
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            Collections.emptyMap(), // fixme
            requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication)
        );
        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new Response<>(responseEntity.getStatusCodeValue(), scaProcessResponse, responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            PaymentServiceTO.fromValue(paymentService),
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            Collections.emptyMap(), // fixme
            requestHeaders.toMap(),
            objectMapper.valueToTree(selectPsuAuthenticationMethod));
        return new Response<>(responseEntity.getStatusCodeValue(), objectMapper.convertValue(responseEntity.getBody(), SelectPsuAuthenticationMethodResponse.class), responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            PaymentServiceTO.fromValue(paymentService),
            PaymentProductTO.fromValue(paymentProduct),
            paymentId, authorisationId,
            Collections.emptyMap(), // fixme
             requestHeaders.toMap(),
            objectMapper.valueToTree(transactionAuthorisation));
        return new Response<>(responseEntity.getStatusCodeValue(), objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class), responseHeadersMapper.getHeaders(responseEntity.getHeaders()));

    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            PaymentServiceTO.fromValue(paymentService),
            PaymentProductTO.fromValue(paymentProduct),
            paymentId, authorisationId,
            Collections.emptyMap(), // fixme
            requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication));
        return new Response<>(responseEntity.getStatusCodeValue(), objectMapper.convertValue(responseEntity.getBody(), UpdatePsuAuthenticationResponse.class), responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }
}
