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

package de.adorsys.xs2a.adapter.adorsys.service.impl;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.StandardPaymentProduct;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.PaymentInitiationRequestResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.SelectPsuAuthenticationMethodResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.mapper.UpdatePsuAuthenticationResponseMapper;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegPaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegSelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegStartScaProcessResponse;
import de.adorsys.xs2a.adapter.adorsys.service.impl.model.AdorsysIntegUpdatePsuAuthenticationResponse;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethodResponse;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthenticationResponse;
import org.mapstruct.factory.Mappers;

public class AdorsysIntegPaymentInitiationService extends BasePaymentInitiationService {
    private final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final PaymentInitiationRequestResponseMapper paymentInitiationRequestResponseMapper = Mappers.getMapper(PaymentInitiationRequestResponseMapper.class);
    private final UpdatePsuAuthenticationResponseMapper updatePsuAuthenticationResponseMapper = Mappers.getMapper(UpdatePsuAuthenticationResponseMapper.class);
    private final SelectPsuAuthenticationMethodResponseMapper selectPsuAuthenticationMethodResponseMapper = Mappers.getMapper(SelectPsuAuthenticationMethodResponseMapper.class);

    public AdorsysIntegPaymentInitiationService(String baseUri) {
        super(baseUri);
    }

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, RequestHeaders requestHeaders, Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct), body, requestHeaders, AdorsysIntegPaymentInitiationRequestResponse.class, paymentInitiationRequestResponseMapper::toPaymentInitiationRequestResponse);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct, String paymentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return startSinglePaymentAuthorisation(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders, updatePsuAuthentication, AdorsysIntegStartScaProcessResponse.class, startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, updatePsuAuthentication, AdorsysIntegUpdatePsuAuthenticationResponse.class, updatePsuAuthenticationResponseMapper::toUpdatePsuAuthenticationResponse);
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod, AdorsysIntegSelectPsuAuthenticationMethodResponse.class, selectPsuAuthenticationMethodResponseMapper::toSelectPsuAuthenticationMethodResponse);
    }
}
