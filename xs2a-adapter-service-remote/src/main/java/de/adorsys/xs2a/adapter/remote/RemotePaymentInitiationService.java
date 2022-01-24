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

package de.adorsys.xs2a.adapter.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.remote.client.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.mapper.ResponseHeadersMapper;
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
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object o) {
        ResponseEntity<PaymentInitationRequestResponse201> responseEntity;
        if (o instanceof String) {
            responseEntity = client.initiatePayment(
                paymentService,
                paymentProduct,
                requestParams.toMap(),
                requestHeaders.toMap(),
                (String) o
            );
        } else if (o instanceof PeriodicPaymentInitiationMultipartBody) {
            PeriodicPaymentInitiationMultipartBody body = (PeriodicPaymentInitiationMultipartBody) o;
            responseEntity = client.initiatePayment(
                paymentService,
                paymentProduct,
                requestParams.toMap(),
                requestHeaders.toMap(),
                body
            );
        } else {
            responseEntity = client.initiatePayment(
                paymentService,
                paymentProduct,
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
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentService.PAYMENTS,
            paymentProduct,
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
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentService.PERIODIC_PAYMENTS,
            paymentProduct,
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
    public Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                                 String paymentId,
                                                                                                 RequestHeaders requestHeaders,
                                                                                                 RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentService.PERIODIC_PAYMENTS,
            paymentProduct,
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap()
        );

        return new Response<>(responseEntity.getStatusCodeValue(),
            (PeriodicPaymentInitiationMultipartBody) responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<String> getPaymentInformationAsString(PaymentService paymentService,
                                                          PaymentProduct paymentProduct,
                                                          String paymentId,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            paymentService,
            paymentProduct,
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            (String) responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct spaymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        ResponseEntity<ScaStatusResponse> responseEntity = client.getPaymentInitiationScaStatus(
            paymentService,
            spaymentProduct,
            paymentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(
            paymentService,
            paymentProduct,
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
    public Response<String> getPaymentInitiationStatusAsString(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(
            paymentService,
            paymentProduct,
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
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        ResponseEntity<Authorisations> responseEntity = client.getPaymentInitiationAuthorisation(
            paymentService,
            paymentProduct,
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        ResponseEntity<StartScaprocessResponse> responseEntity = client.startPaymentAuthorisation(
            paymentService,
            paymentProduct,
            paymentId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            new ObjectNode(JsonNodeFactory.instance));
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponse> responseEntity = client.startPaymentAuthorisation(
            paymentService,
            paymentProduct,
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
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            paymentService,
            paymentProduct,
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
    public Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                            PaymentProduct paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            paymentService,
            paymentProduct,
            paymentId, authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(transactionAuthorisation));
        return new Response<>(responseEntity.getStatusCodeValue(),
            objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));

    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(
            paymentService,
            paymentProduct,
            paymentId, authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication));
        return new Response<>(responseEntity.getStatusCodeValue(),
            objectMapper.convertValue(responseEntity.getBody(), UpdatePsuAuthenticationResponse.class),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }
}
