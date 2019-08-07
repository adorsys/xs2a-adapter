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

package de.adorsys.xs2a.adapter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.remote.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.mapper.*;
import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.model.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {

    private final PaymentInitiationClient client;

    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper = new PaymentInitiationScaStatusResponseMapper();
    private final PaymentInitiationRequestResponseMapper initiationRequestResponseMapper = Mappers.getMapper(PaymentInitiationRequestResponseMapper.class);
    private final SinglePaymentInformationMapper singlePaymentInformationMapper = Mappers.getMapper(SinglePaymentInformationMapper.class);
    private final PaymentInitiationStatusMapper paymentInitiationStatusMapper = Mappers.getMapper(PaymentInitiationStatusMapper.class);
    private final PaymentInitiationAuthorisationResponseMapper authorisationResponseMapper = Mappers.getMapper(PaymentInitiationAuthorisationResponseMapper.class);
    private final StartScaProcessResponseMapper scaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentInitiationServiceImpl(PaymentInitiationClient paymentInitiationClient) {
        this.client = paymentInitiationClient;
    }

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, RequestHeaders requestHeaders, Object o) {

        ResponseEntity<PaymentInitationRequestResponse201TO> responseEntity;
        if (o instanceof String) {
            responseEntity = client.initiatePayment(
                PaymentServiceTO.PAYMENTS,
                PaymentProductTO.fromValue(paymentProduct),
                requestHeaders.toMap(),
                (String) o
            );
        } else {
            responseEntity = client.initiatePayment(
                PaymentServiceTO.PAYMENTS,
                PaymentProductTO.fromValue(paymentProduct),
                requestHeaders.toMap(),
                objectMapper.valueToTree(o)
            );
        }
        PaymentInitiationRequestResponse paymentInitiationRequestResponse = initiationRequestResponseMapper.toPaymentInitiationRequestResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), paymentInitiationRequestResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        ResponseEntity<Object> responseEntity = client.getPaymentInformation(
            PaymentServiceTO.PAYMENTS,
            PaymentProductTO.fromValue(paymentProduct),
            paymentId,
            requestHeaders.toMap()
        );
        PaymentInitiationWithStatusResponseTO responseTO = objectMapper.convertValue(responseEntity.getBody(), PaymentInitiationWithStatusResponseTO.class);
        SinglePaymentInitiationInformationWithStatusResponse paymentInitiationSctWithStatusResponse = singlePaymentInformationMapper.toSinglePaymentInitiationInformationWithStatusResponse(responseTO);
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), paymentInitiationSctWithStatusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService, String spaymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders) {
        ResponseEntity<ScaStatusResponseTO> responseEntity = client.getPaymentInitiationScaStatus(PaymentServiceTO.PAYMENTS, PaymentProductTO.fromValue(paymentService), spaymentProduct, paymentId, requestHeaders.toMap());
        PaymentInitiationScaStatusResponse initiationScaStatusResponse = paymentInitiationScaStatusResponseMapper.toPaymentInitiationScaStatusResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), initiationScaStatusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(PaymentServiceTO.PAYMENTS, PaymentProductTO.fromValue(paymentProduct), paymentId, requestHeaders.toMap());
        PaymentInitiationStatus initiationStatus = paymentInitiationStatusMapper.toPaymentInitiationStatus((PaymentInitiationStatusResponse200JsonTO) responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), initiationStatus, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<String> getSinglePaymentInitiationStatusAsString(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        ResponseEntity<Object> responseEntity = client.getPaymentInitiationStatus(PaymentServiceTO.PAYMENTS, PaymentProductTO.fromValue(paymentProduct), paymentId, requestHeaders.toMap());
        try {
            String responseObject = objectMapper.writeValueAsString(responseEntity.getBody());
            return new GeneralResponse<>(responseEntity.getStatusCodeValue(), responseObject, getHeaders(responseEntity.getHeaders()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), "{}", getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        ResponseEntity<AuthorisationsTO> responseEntity = client.getPaymentInitiationAuthorisation(PaymentServiceTO.fromValue(paymentService), PaymentProductTO.fromValue(paymentProduct), paymentId, requestHeaders.toMap());
        PaymentInitiationAuthorisationResponse authorisationResponse = authorisationResponseMapper.toPaymentInitiationAuthorisationResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), authorisationResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct, String paymentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startPaymentAuthorisation(
            PaymentServiceTO.PAYMENTS,
            PaymentProductTO.fromValue(paymentProduct),
            paymentId, requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication)
        );
        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaProcessResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(PaymentServiceTO.fromValue(paymentService), PaymentProductTO.fromValue(paymentProduct), paymentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(selectPsuAuthenticationMethod));
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), objectMapper.convertValue(responseEntity.getBody(), SelectPsuAuthenticationMethodResponse.class), getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(PaymentServiceTO.fromValue(paymentService), PaymentProductTO.fromValue(paymentProduct), paymentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(transactionAuthorisation));
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class), getHeaders(responseEntity.getHeaders()));

    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updatePaymentPsuData(PaymentServiceTO.fromValue(paymentService), PaymentProductTO.fromValue(paymentProduct), paymentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), objectMapper.convertValue(responseEntity.getBody(), UpdatePsuAuthenticationResponse.class), getHeaders(responseEntity.getHeaders()));
    }

    private ResponseHeaders getHeaders(HttpHeaders httpHeaders) {
        Set<Map.Entry<String, List<String>>> entrySet = httpHeaders.entrySet();
        Map<String, String> headers = new HashMap<>(entrySet.size());
        for (Map.Entry<String, List<String>> entry : entrySet) {
            List<String> value = entry.getValue();
            if (value != null && !value.isEmpty()) {
                headers.put(entry.getKey(), value.get(0));
            }
        }
        return ResponseHeaders.fromMap(headers);
    }
}
