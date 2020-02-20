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

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.StandardPaymentProduct;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.impl.mapper.PaymentInitiationRequestResponseDkbMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.SelectPsuAuthenticationMethodResponseDkbMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.StartScaProcessResponseDkbMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UpdatePsuAuthenticationResponseDkbMapper;
import de.adorsys.xs2a.adapter.service.impl.model.DkbPaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.service.impl.model.DkbSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.service.impl.model.DkbStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.impl.model.DkbUpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.service.model.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class DkbPaymentInitiationService extends BasePaymentInitiationService {
    private final PaymentInitiationRequestResponseDkbMapper paymentInitiationRequestResponseMapper =
        Mappers.getMapper(PaymentInitiationRequestResponseDkbMapper.class);
    private final StartScaProcessResponseDkbMapper startScaProcessResponseMapper =
        Mappers.getMapper(StartScaProcessResponseDkbMapper.class);
    private final UpdatePsuAuthenticationResponseDkbMapper updatePsuAuthenticationResponseMapper =
        Mappers.getMapper(UpdatePsuAuthenticationResponseDkbMapper.class);
    private final SelectPsuAuthenticationMethodResponseDkbMapper selectPsuAuthenticationMethodResponseMapper =
        Mappers.getMapper(SelectPsuAuthenticationMethodResponseDkbMapper.class);

    private AccessTokenService accessService;

    public DkbPaymentInitiationService(String baseUri, AccessTokenService accessService, HttpClient httpClient) {
        super(baseUri, httpClient);
        this.accessService = accessService;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct),
            body,
            requestHeaders,
            requestParams,
            DkbPaymentInitiationRequestResponse.class,
            paymentInitiationRequestResponseMapper::toPaymentInitiationRequestResponse);
    }

    @Override
    public Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams,
                                                                             UpdatePsuAuthentication updatePsuAuthentication) {
        return startSinglePaymentAuthorisation(StandardPaymentProduct.fromSlug(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            DkbStartScaProcessResponse.class,
            startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, updatePsuAuthentication, DkbUpdatePsuAuthenticationResponse.class, updatePsuAuthenticationResponseMapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod, DkbSelectPsuAuthenticationMethodResponse.class, selectPsuAuthenticationMethodResponseMapper::toSelectPsuAuthenticationMethodResponse);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        return addBearerHeader(map);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return addBearerHeader(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return addBearerHeader(headers);
    }

    Map<String, String> addBearerHeader(Map<String, String> map) {
        map.put("Authorization", "Bearer " + accessService.retrieveToken());
        return map;
    }
}
