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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.remote.api.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.service.mapper.ResponseHeadersMapper;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class RemotePaymentInitiationService implements PaymentInitiationService {

    private static final Logger log = LoggerFactory.getLogger(RemotePaymentInitiationService.class);

    private final PaymentInitiationClient client;
    private final ObjectMapper objectMapper;
    private final ResponseHeadersMapper responseHeadersMapper = Mappers.getMapper(ResponseHeadersMapper.class);

    public RemotePaymentInitiationService(PaymentInitiationClient paymentInitiationClient) {
        this(paymentInitiationClient, new ObjectMapper());
    }

    public RemotePaymentInitiationService(PaymentInitiationClient paymentInitiationClient, ObjectMapper objectMapper) {
        this.client = paymentInitiationClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                        String paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object o) {
        ResponseEntity<PaymentInitationRequestResponse201> responseEntity;
        if (o instanceof String) {
            responseEntity = client.initiatePayment(
                PaymentService.fromValue(paymentService),
                PaymentProduct.fromValue(paymentProduct),
                requestParams.toMap(),
                requestHeaders.toMap(),
                (String) o
            );
        } else if (o instanceof PeriodicPaymentInitiationMultipartBody) {
            PeriodicPaymentInitiationMultipartBody body = (PeriodicPaymentInitiationMultipartBody) o;
            responseEntity = client.initiatePayment(
                PaymentService.fromValue(paymentService),
                PaymentProduct.fromValue(paymentProduct),
                requestParams.toMap(),
                requestHeaders.toMap(),
                body
            );
        } else {
            responseEntity = client.initiatePayment(
                PaymentService.fromValue(paymentService),
                PaymentProduct.fromValue(paymentProduct),
                requestParams.toMap(),
                requestHeaders.toMap(),
                objectMapper.valueToTree(o)
            );
        }
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentService.PAYMENTS,
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap()
        );
        PaymentInitiationWithStatusResponse response =
            objectMapper.convertValue(responseEntity.getBody(), PaymentInitiationWithStatusResponse.class);
        return new Response<>(responseEntity.getStatusCodeValue(),
            response,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(String paymentProduct,
                                                                                                          String paymentId,
                                                                                                          RequestHeaders requestHeaders,
                                                                                                          RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentService.PERIODIC_PAYMENTS,
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap()
        );

        PeriodicPaymentInitiationWithStatusResponse response = objectMapper.convertValue(responseEntity.getBody(),
            PeriodicPaymentInitiationWithStatusResponse.class);

        return new Response<>(responseEntity.getStatusCodeValue(), response,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<String> getPaymentInformationAsString(String paymentService,
                                                          String paymentProduct,
                                                          String paymentId,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            (String) responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                     String spaymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        ResponseEntity<ScaStatusResponse> responseEntity = client.getPaymentInitiationScaStatus(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(spaymentProduct),
            paymentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap());
        PaymentInitiationStatusResponse200Json response =
            objectMapper.convertValue(responseEntity.getBody(), PaymentInitiationStatusResponse200Json.class);
        return new Response<>(responseEntity.getStatusCodeValue(),
            response,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<String> getPaymentInitiationStatusAsString(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
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
    public Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
                                                                      String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        ResponseEntity<Authorisations> responseEntity = client.getPaymentInitiationAuthorisation(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        ResponseEntity<StartScaprocessResponse> responseEntity = client.startPaymentAuthorisation(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            new ObjectNode(JsonNodeFactory.instance));
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponse> responseEntity = client.startPaymentAuthorisation(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication)
        );
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                String paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(selectPsuAuthenticationMethod));
        return new Response<>(responseEntity.getStatusCodeValue(),
            objectMapper.convertValue(responseEntity.getBody(), SelectPsuAuthenticationMethodResponse.class),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                            String paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId, authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(transactionAuthorisation));
        return new Response<>(responseEntity.getStatusCodeValue(),
            objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));

    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                          String paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId, authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication));
        return new Response<>(responseEntity.getStatusCodeValue(),
            objectMapper.convertValue(responseEntity.getBody(), UpdatePsuAuthenticationResponse.class),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }
}
