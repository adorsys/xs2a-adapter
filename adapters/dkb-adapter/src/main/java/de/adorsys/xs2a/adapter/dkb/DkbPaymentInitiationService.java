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

package de.adorsys.xs2a.adapter.dkb;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.dkb.mapper.PaymentInitiationRequestResponseDkbMapper;
import de.adorsys.xs2a.adapter.dkb.mapper.SelectPsuAuthenticationMethodResponseDkbMapper;
import de.adorsys.xs2a.adapter.dkb.mapper.StartScaProcessResponseDkbMapper;
import de.adorsys.xs2a.adapter.dkb.mapper.UpdatePsuAuthenticationResponseDkbMapper;
import de.adorsys.xs2a.adapter.dkb.model.DkbPaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.dkb.model.DkbSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.dkb.model.DkbStartScaProcessResponse;
import de.adorsys.xs2a.adapter.dkb.model.DkbUpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
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

    public DkbPaymentInitiationService(Aspsp aspsp,
                                       AccessTokenService accessService,
                                       HttpClient httpClient,
                                       LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
        this.accessService = accessService;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                      String paymentProduct,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams,
                                                                      Object body) {
        return initiatePayment(paymentService,
            paymentProduct,
            requestHeaders,
            requestParams,
            body,
            DkbPaymentInitiationRequestResponse.class,
            paymentInitiationRequestResponseMapper::toPaymentInitiationRequestResponse);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            DkbStartScaProcessResponse.class,
            startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                          String paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            DkbUpdatePsuAuthenticationResponse.class,
            updatePsuAuthenticationResponseMapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                String paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod,
            DkbSelectPsuAuthenticationMethodResponse.class,
            selectPsuAuthenticationMethodResponseMapper::toSelectPsuAuthenticationMethodResponse);
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
